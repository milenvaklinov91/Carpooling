package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.exceptions.UnauthorizedOperationException;
import com.telerikacademy.carpooling.models.Feedback;
import com.telerikacademy.carpooling.models.FeedbackComment;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.repositories.FeedbackCommentRepositoryImpl;
import com.telerikacademy.carpooling.repositories.interfaces.FeedbackCommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FeedbackCommentServiceImplTest {

    @Mock
    FeedbackCommentRepositoryImpl feedbackCommentRepository;
    @InjectMocks
    FeedbackCommentServiceImpl feedbackCommentService;

    @Test
    void getFeedbackCommentById_Should_ReturnFeedbackComment_When_MatchExists() {
        FeedbackComment mockFeedbackComment = Helper.createFeedbackComment();
        when(feedbackCommentRepository.getFeedbackCommentById(Mockito.anyInt()))
                .thenReturn(mockFeedbackComment);

        FeedbackComment result = feedbackCommentService.getFeedbackCommentById(mockFeedbackComment.getFeedbackCommentId());

        assertEquals(mockFeedbackComment, result);
    }

    @Test
    void getAllFeedbackCommentsByFeedbackId_Should_Return_ListOfAllFeedbackCommentsByFeedbackId() {
        List<FeedbackComment> allFeedbackCommentsByFeedbackId = new ArrayList<>();
        FeedbackComment mockFeedbackComment = Helper.createFeedbackComment();
        when(feedbackCommentRepository.findFeedbackCommentsByFeedbackId(mockFeedbackComment.getFeedback().getFeedbackId()))
                .thenReturn(allFeedbackCommentsByFeedbackId);

        List<FeedbackComment> result = feedbackCommentService.findFeedbackCommentsByFeedbackId(mockFeedbackComment.getFeedback().getFeedbackId());

        assertEquals(allFeedbackCommentsByFeedbackId, result);
    }

    @Test
    void createFeedbackComment_Should_ThrowUnauthorizedException_When_UserIsBlocked() {
        FeedbackComment mockFeedbackComment = Helper.createFeedbackComment();
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(true);

        assertThrows(UnauthorizedOperationException.class, () -> feedbackCommentService.create(mockFeedbackComment, mockUser));
    }

    @Test
    void createFeedbackComment_Should_CallRepositoryCreate_When_UserIsNotBlocked() {
        FeedbackComment mockFeedbackComment = Helper.createFeedbackComment();
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);
        doNothing().when(feedbackCommentRepository).create(mockFeedbackComment);

        feedbackCommentService.create(mockFeedbackComment, mockUser);

        verify(feedbackCommentRepository, VerificationModeFactory.times(1)).create(mockFeedbackComment);
    }

    @Test
    void modifyFeedbackComment_Should_CallRepositoryModify_When_UserIsAuthorized() {
        FeedbackComment mockFeedbackComment = Helper.createFeedbackComment();
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);

        mockFeedbackComment.setUserCreatedBy(mockUser);

        feedbackCommentService.modify(mockFeedbackComment, mockUser);

        verify(feedbackCommentRepository, times(1)).modify(mockFeedbackComment);
    }

    @Test
    void modifyFeedbackComment_Should_ThrowUnauthorizedException_When_UserIsBlocked() {
        FeedbackComment mockFeedbackComment = Helper.createFeedbackComment();
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(true);

        assertThrows(UnauthorizedOperationException.class, () -> feedbackCommentService.modify(mockFeedbackComment, mockUser));
    }

    @Test
    void modifyFeedbackComment_Should_ThrowUnauthorizedException_When_UserIsNotAuthorized() {
        FeedbackComment mockFeedbackComment = Helper.createFeedbackComment();
        User mockUser = Helper.createMockUser();
        User mockUser2 = Helper.createSecondMockUser();

        mockFeedbackComment.setUserCreatedBy(mockUser);

        assertThrows(UnauthorizedOperationException.class, () -> feedbackCommentService.modify(mockFeedbackComment, mockUser2));
    }

    @Test
    public void deleteFeedbackComment_ShouldThrowException_WhenUserIsBlocked() {
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(true);
        FeedbackComment mockFeedbackComment = Helper.createFeedbackComment();

        assertThrows(NullPointerException.class, () -> {
            feedbackCommentService.deleteComment(mockFeedbackComment.getFeedbackCommentId(), mockUser);
        });

        verify(feedbackCommentRepository, never()).deleteComment(anyInt());
    }

    @Test
    public void deleteFeedbackComment_ShouldThrowException_WhenUserIsNotAdminOrCreator() {
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);
        mockUser.setAdmin(false);

        FeedbackComment mockFeedbackComment = Helper.createFeedbackComment();
        User secondMockUser = Helper.createSecondMockUser();
        mockFeedbackComment.setUserCreatedBy(secondMockUser);

        when(feedbackCommentRepository.getFeedbackCommentById(anyInt())).thenReturn(mockFeedbackComment);

        assertThrows(UnauthorizedOperationException.class, () -> {
            feedbackCommentService.deleteComment(mockFeedbackComment.getFeedbackCommentId(), mockUser);
        });

        verify(feedbackCommentRepository, never()).deleteComment(anyInt());
    }

    @Test
    public void deleteFeedbackComment_ShouldDeleteFeedbackComment_WhenUserIsAdmin() {
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);
        mockUser.setAdmin(true);

        FeedbackComment mockFeedbackComment = Helper.createFeedbackComment();
        User secondMockUser = Helper.createSecondMockUser();
        mockFeedbackComment.setUserCreatedBy(secondMockUser);

        when(feedbackCommentRepository.getFeedbackCommentById(anyInt())).thenReturn(mockFeedbackComment);

        feedbackCommentService.deleteComment(mockFeedbackComment.getFeedbackCommentId(), mockUser);

        verify(feedbackCommentRepository, times(1)).deleteComment(1);
    }

    @Test
    public void deleteFeedbackComment_ShouldDeleteFeedbackComment_WhenUserIsOwner() {
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);
        mockUser.setAdmin(false);

        FeedbackComment mockFeedbackComment = Helper.createFeedbackComment();
        User secondMockUser = Helper.createSecondMockUser();
        mockFeedbackComment.setUserCreatedBy(secondMockUser);

        when(feedbackCommentRepository.getFeedbackCommentById(anyInt())).thenReturn(mockFeedbackComment);

        feedbackCommentService.deleteComment(mockFeedbackComment.getFeedbackCommentId(), secondMockUser);

        verify(feedbackCommentRepository, times(1)).deleteComment(1);
    }







}
