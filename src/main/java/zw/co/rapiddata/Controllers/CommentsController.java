package zw.co.rapiddata.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.rapiddata.DTOs.CommentsDTO;
import zw.co.rapiddata.Models.Comments;
import zw.co.rapiddata.Services.CommentsServices;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentsController {

    private final CommentsServices commentsServices;

    public CommentsController(CommentsServices commentsServices) {
        this.commentsServices = commentsServices;
    }

    @GetMapping("/property/{propertyId}")
    public List<CommentsDTO> getAllCommentsForProperty(@PathVariable Long propertyId){
        return commentsServices.getAllComments(propertyId);
    }

    @PostMapping("/create")
    public Comments createComment(@RequestBody Comments comment, @RequestParam Long propertyId, Principal principal){
        return commentsServices.createComment(comment, propertyId, principal);
    }

    @GetMapping("/{commentId}")
    public CommentsDTO getComment(@PathVariable Long commentId){
        return commentsServices.getComment(commentId);
    }


}
