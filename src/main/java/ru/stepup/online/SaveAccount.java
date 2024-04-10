package ru.stepup.online;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SaveAccount {

    private List<Change> saveAccount;

    private SaveAccount(List<Change> saveAccount) {
        this.saveAccount = saveAccount;
    }

    public static SaveAccount add(List<Change> saveAccount){
        return new SaveAccount(saveAccount);
    }

    public List<Change> getSaveAccount() {
        return saveAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaveAccount that = (SaveAccount) o;
        return Objects.equals(saveAccount, that.saveAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saveAccount);
    }
}
