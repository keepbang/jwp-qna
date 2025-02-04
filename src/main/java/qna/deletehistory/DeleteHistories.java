package qna.deletehistory;

import qna.question.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeleteHistories {
    private final List<DeleteHistory> deleteHistories;

    private DeleteHistories(List<DeleteHistory> deleteHistories) {
        this.deleteHistories = deleteHistories;
    }

    public static DeleteHistories fromDeleteHistoriesByQuestion(Question question) {
        List<DeleteHistory> deleteHistories = new ArrayList<>();
        deleteHistories.add(DeleteHistory.fromQuestion(question));
        deleteHistories.addAll(question.createAnswersDeleteHistories());
        return new DeleteHistories(deleteHistories);
    }

    public List<DeleteHistory> getDeleteHistories() {
        return new ArrayList<>(deleteHistories);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeleteHistories that = (DeleteHistories) o;
        return Objects.equals(deleteHistories, that.deleteHistories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deleteHistories);
    }
}
