package qna.deletehistory;

import qna.answer.Answer;
import qna.domain.ContentType;
import qna.question.Question;
import qna.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "delete_history")
public class DeleteHistory{
    @Column(name = "created_date")
    private final LocalDateTime createdDate = LocalDateTime.now();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "content_type")
    @Enumerated(EnumType.STRING)
    private ContentType contentType;
    @Column(name = "content_id")
    private Long contentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delete_by_id", foreignKey = @ForeignKey(name = "fk_delete_history_to_user"))
    private User deletedByUser;

    protected DeleteHistory() {
    }

    public DeleteHistory(final ContentType contentType, final Long contentId, final User deletedByUser) {
        this(null, contentType, contentId, deletedByUser);
    }

    public DeleteHistory(final Long id, final ContentType contentType, final Long contentId, final User deletedByUser) {
        Objects.requireNonNull(deletedByUser);
        this.id = id;
        this.contentType = contentType;
        this.contentId = contentId;
        this.deletedByUser = deletedByUser;
    }

    public static List<DeleteHistory> ofDeleteHistoriesByQuestion(Question question) {
        List<DeleteHistory> deleteHistories = new ArrayList<>();
        deleteHistories.add(DeleteHistory.fromQuestion(question));
        deleteHistories.addAll(question.createAnswersDeleteHistories());
        return deleteHistories;
    }

    public static DeleteHistory fromAnswer(Answer answer) {
        return new DeleteHistory(ContentType.ANSWER, answer.getId(), answer.getUser());
    }

    public static DeleteHistory fromQuestion(Question question) {
        return new DeleteHistory(ContentType.QUESTION, question.getId(), question.getUser());
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeleteHistory that = (DeleteHistory) o;
        return Objects.equals(id, that.getId())
                && contentType == that.contentType
                && Objects.equals(contentId, that.contentId)
                && Objects.equals(deletedByUser, that.deletedByUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contentType, contentId, deletedByUser);
    }

    @Override
    public String toString() {
        return "DeleteHistory{" +
                "id=" + id +
                ", contentType=" + contentType +
                ", contentId=" + contentId +
                ", deletedByUser=" + deletedByUser +
                ", createdDate=" + createdDate +
                '}';
    }
}
