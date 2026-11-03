import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<String, Account> accounts = new HashMap<>();

    public boolean addAccount(Account account) {
        if (account == null) return false;

        String key = normalize(account.getUserId());
        if (key == null || accounts.containsKey(key)) return false;

        accounts.put(key, account);
        return true;
    }


    public Account findAccount(String id) {
        String key = normalize(id);
        return key == null ? null : accounts.get(key);
    }


    public Account authenticate(String id, String pin) {
        Account a = findAccount(id);
        return (a != null && a.checkPin(pin)) ? a : null;
    }


    private String normalize(String id) {
        if (id == null) return null;
        String trimmed = id.trim().toLowerCase();
        return trimmed.isEmpty() ? null : trimmed;
    }
}