package zw.co.rapiddata.Services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import zw.co.rapiddata.Config.Repositories.UserRepository;
import zw.co.rapiddata.DTOs.CommentsDTO;
import zw.co.rapiddata.Models.Comments;
import zw.co.rapiddata.Models.Property;
import zw.co.rapiddata.Repositories.CommentsRepository;
import zw.co.rapiddata.Repositories.PropertyRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CommentsServices {

    private final CommentsRepository commentsRepository;

    private final PropertyRepository propertyRepository;

    private final UserRepository userRepository;

    public CommentsServices(CommentsRepository commentsRepository, PropertyRepository propertyRepository, UserRepository userRepository) {
        this.commentsRepository = commentsRepository;
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    public Comments createComment(Comments comment, Long propertyId, Principal principal) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new EntityNotFoundException("Property not found with id " + propertyId));
        comment.setUser(userRepository.findByEmail(principal.getName()).orElse(null));
        comment.setProperty(property);
        return commentsRepository.save(comment);
    }

    public Comments updateComment(Long commentId, Comments commentDetails) {
        Comments comment = commentsRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found with id " + commentId));
        comment.setContent(commentDetails.getContent());
        return commentsRepository.save(comment);
    }

    public String deleteComment(Long commentId, Principal principal) {
        Comments comment = commentsRepository.findById(commentId).orElse(null);
        assert comment != null;
        if(Objects.equals(comment.getUser().getId(), userRepository.findByEmail(principal.getName()).orElseThrow().getId())){
            commentsRepository.deleteById(commentId);
            return "Comment Deleted!";
        }

        return "Comment Did Not Belong To You!";
    }

    public CommentsDTO getComment(Long commentId) {
        Comments comment = commentsRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found with id " + commentId));
        return new CommentsDTO(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getFirstname());
    }

    public List<CommentsDTO> getAllComments(Long propertyId) {
        List<Comments> comments = commentsRepository.findAllByPropertyId(propertyId);
        List<CommentsDTO> commentsDTOs = new ArrayList<>();
        for (Comments comment : comments) {
            commentsDTOs.add(new CommentsDTO(
                    comment.getId(),
                    comment.getContent(),
                    comment.getUser().getFirstname()));
        }
        return commentsDTOs;
    }



    //WITH TOKENS

    public Comments getCommentWithToken(Long commentId) {
        return commentsRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found with id " + commentId));
    }

    public List<Comments> getAllCommentsWithToken(Long propertyId) {
        return commentsRepository.findAllByPropertyId(propertyId);
    }
}
