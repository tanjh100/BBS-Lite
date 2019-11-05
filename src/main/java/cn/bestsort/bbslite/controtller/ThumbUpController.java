package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.enums.FunctionItem;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.pojo.model.Article;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.CommentService;
import cn.bestsort.bbslite.service.ArticleService;
import cn.bestsort.bbslite.service.ThumbUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @ClassName ThumbArticle
 * @Description
 * @Author bestsort
 * @Date 2019/10/14 下午2:17
 * @Version 1.0
 */

@Controller
public class ThumbUpController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ThumbUpService thumbUpService;
    @Autowired
    private CommentService commentService;
    @ResponseBody
    @PostMapping("/thumbUpArticle")
    public ResultDto thumbUpArticle(@RequestParam("id") Long id,
                                     @RequestParam("isActive") Boolean isActive,
                                     @RequestParam("type") FunctionItem type,
                                     HttpSession session){
        User user = (User)session.getAttribute("user");
        if (user == null){
            return new ResultDto().errorOf(CustomizeErrorCodeEnum.NO_LOGIN);
        }
        //TODO id合法性校验
        boolean active = thumbUpService.setThumbUpCount(id,user.getId(), type,isActive);
        switch (type){
            case ARTICLE:articleService.incArticleLike(id,isActive?-1L:1L);break ;
            case COMMENT:commentService.incCommentLike(id,isActive?-1L:1L);break;
            default:throw new CustomizeException(CustomizeErrorCodeEnum.USER_ERROR);
        }
        return new ResultDto().okOf()
                .addMsg("isActive",active);
    }
}
