package qna.user;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Password {
    private static final int MAX_LENGTH_PASSWORD = 20;
    private static final String INVALID_PASSWORD_MESSAGE = "비밀번호는 필수이며 길이는 1이상 20이하여야 합니다.";

    @Column(name = "password", nullable = false, length = MAX_LENGTH_PASSWORD)
    private String password;

    public Password(String password) {
        this.password = password;
        validatePassword();
    }

    protected Password() {
    }

    private void validatePassword() {
        if (Objects.isNull(password) || isInvalidPasswordLength()) {
            throw new IllegalArgumentException(INVALID_PASSWORD_MESSAGE);
        }
    }

    private boolean isInvalidPasswordLength() {
        return password.isEmpty() || password.length() > MAX_LENGTH_PASSWORD;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password1 = (Password) o;
        return Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}
