package eu.mnrdesign.matned.final_project.holder;

public class AccountHolder {

    private static AccountHolder instance;

    public static AccountHolder getInstance(){
        if(instance == null) {
            synchronized (AccountHolder.class) {
                if(instance == null)
                    instance = new AccountHolder();
            }
        }
        return instance;
    }

    private Long selectedAccountId;

    private AccountHolder() {
        if (instance != null) {
            throw new IllegalStateException("Cannot create new instance, please use getInstance method instead.");
        }
    }

    public static void setInstance(AccountHolder instance) {
        AccountHolder.instance = instance;
    }

    public Long getSelectedAccountId() {
        return selectedAccountId;
    }

    public void setSelectedAccountId(Long selectedAccountId) {
        this.selectedAccountId = selectedAccountId;
    }
}
