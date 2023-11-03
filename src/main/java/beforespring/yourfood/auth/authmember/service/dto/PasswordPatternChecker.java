package beforespring.yourfood.auth.authmember.service.dto;

import beforespring.yourfood.auth.authmember.service.exception.PasswordPatternException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PasswordPatternChecker {
    /**
     * 비밀번호의 유효성을 검사합니다.
     *
     * @param password 검증할 비밀번호
     */
    public void checkPasswordPattern(String password) {
        checkConsecutiveChars(password);
        checkCharsCombination(password);
    }

    /**
     * 연속된 문자 검사. 동일한 문자가 3회 이상 나오면 예외 발생
     *
     * @param toCheck 검증할 문자열
     */
    private void checkConsecutiveChars(String toCheck) {
        String consecutiveCharsPattern = "(.)\\1\\1";
        Matcher matcher = Pattern.compile(consecutiveCharsPattern).matcher(toCheck);
        if (matcher.find()) {
            throw new PasswordPatternException("비밀번호에 동일한 문자를 3회 이상 연속으로 사용할 수 없습니다.");
        }
    }

    /**
     * 문자 조합 검사 로직. 문자, 숫자, 특수 문자 중 2가지 이상을 포함하지 않으면 예외 발생
     *
     * @param toCheck 확인할 문자열
     */
    private void checkCharsCombination(String toCheck) {
        boolean alpha = containsAlpha(toCheck);
        boolean num = containsNum(toCheck);
        boolean specialChar = containsSChar(toCheck);

        if (!(alpha && (num || specialChar)) && !(num && specialChar)) {
            throw new PasswordPatternException("비밀번호는 숫자, 문자, 특수문자 중 2가지 이상을 포함해야 합니다.");
        }
    }

    /**
     * 문자열에 알파벳이 포함되는지 여부
     *
     * @param toCheck 확인할 문자열
     * @return 포함되면 true
     */
    private boolean containsAlpha(String toCheck) {
        String alphaPattern = ".*[A-Za-z].*";
        return toCheck.matches(alphaPattern);
    }

    /**
     * 문자열에 숫자가 포함되는지 여부
     *
     * @param toCheck 확인할 문자열
     * @return 포함되면 true
     */
    private boolean containsNum(String toCheck) {
        String numPattern = ".*\\d.*";
        return toCheck.matches(numPattern);
    }

    /**
     * 문자열에 특수문자가 포함되는지 여부
     *
     * @param toCheck 확인할 문자열
     * @return 포함되면 true
     */
    private boolean containsSChar(String toCheck) {
        String sCharPattern = ".*[^A-Za-z\\d].*";
        return toCheck.matches(sCharPattern);
    }
}
