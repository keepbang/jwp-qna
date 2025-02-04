package qna.user;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Name {
    private static final int MAX_LENGTH_NAME = 20;
    private static final String INVALID_NAME_MESSAGE = "사용자 이름은 필수이며 길이는 1이상 20이하여야 합니다.";

    @Column(name = "name", nullable = false, length = MAX_LENGTH_NAME)
    private String name;

    public Name(String name) {
        this.name = name;
        validateName();
    }

    protected Name() {
    }

    private void validateName() {
        if (Objects.isNull(name) || isInvalidNameLength()) {
            throw new IllegalArgumentException(INVALID_NAME_MESSAGE);
        }
    }

    private boolean isInvalidNameLength() {
        return name.isEmpty() || name.length() > MAX_LENGTH_NAME;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name1 = (Name) o;
        return Objects.equals(name, name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
