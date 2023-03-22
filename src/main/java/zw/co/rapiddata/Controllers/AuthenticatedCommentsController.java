package zw.co.rapiddata.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.rapiddata.Models.Comments;
import zw.co.rapiddata.Services.CommentsServices;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/comments")
public class AuthenticatedCommentsController {

    private final CommentsServices commentsServices;

    public AuthenticatedCommentsController(CommentsServices commentsServices) {
        this.commentsServices = commentsServices;
    }


    @PreAuthorize("hasAnyAuthority('SCOPE_OWNER','SCOPE_ADMIN','SCOPE_TENANT')")
    @GetMapping("/{commentId}")
    public Comments getCommentWithToken(@PathVariable Long commentId){
        return commentsServices.getCommentWithToken(commentId);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_OWNER','SCOPE_ADMIN','SCOPE_TENANT')")
    @GetMapping("/property/{propertyId}")
    public List<Comments> getAllCommentsForPropertyWithToken(@PathVariable Long propertyId){
        return commentsServices.getAllCommentsWithToken(propertyId);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_OWNER','SCOPE_ADMIN','SCOPE_TENANT')")
    @DeleteMapping("/delete/{commentId}")
    public String deleteComment(@PathVariable Long commentId, Principal principal){
        return commentsServices.deleteComment(commentId, principal);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_OWNER','SCOPE_ADMIN','SCOPE_TENANT')")
    @PutMapping("/update/{propertyId}")
    public Comments updateComment(@RequestBody Comments comment, @PathVariable Long propertyId){
        return commentsServices.updateComment(propertyId, comment);
    }
}
