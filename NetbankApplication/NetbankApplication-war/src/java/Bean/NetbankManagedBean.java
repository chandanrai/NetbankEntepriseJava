package Bean;

import Session.Stateless.AccountFacadeRemote;
import Session.Stateless.AccountStatementRemote;
import Session.Stateless.LoginRemote;
import Session.Stateless.ResetPasswordRemote;
import Session.Stateless.TrasferFundsRemote;
import Session.Stateless.ViewBalanceRemote;
import entities.Account;
import entities.Transactions;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author chandan
 */
@Named(value = "netbankManagedBean")
@SessionScoped
public class NetbankManagedBean implements Serializable {
    @EJB
    private AccountFacadeRemote accountFacade;
    @EJB
    private ResetPasswordRemote resetPassword;
    @EJB
    private AccountStatementRemote accountStatement;
    @EJB
    private TrasferFundsRemote trasferFunds;
    @EJB
    private LoginRemote login;
    @EJB
    private ViewBalanceRemote viewBalance;
    private String accountNo;
    private String name;
    private String email;
    private String balance;
    private String password;
    private String phoneNo;
    private String toAccount;
    private float amount;
    private List<Transactions> transactions;

    public NetbankManagedBean() {
    }

    public List<Transactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transactions> transactions) {
        this.transactions = transactions;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }
    
    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getSecQues() {
        return secQues;
    }

    public void setSecQues(String secQues) {
        this.secQues = secQues;
    }

    public String getSecAns() {
        return secAns;
    }

    public void setSecAns(String secAns) {
        this.secAns = secAns;
    }
    private String secQues;
    private String secAns;
    
    public void showBalance(){
        System.out.println("IN Balance");
        balance = viewBalance.viewBalance(accountNo);
    }
    
    public String checkLogin(){
        if(login.doLogin(accountNo, password)){
            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context.
                    getExternalContext().getSession(true);
            session.setAttribute("account", accountNo);
            return "success";
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    ("Account number or password incorrect"));
            setAccountNo(null);
            setPassword(null);
            return null;
        }
    }
    
    public void transferMoney(){
        String result = trasferFunds.transferFunds(accountNo, toAccount,
                amount);
        FacesContext.getCurrentInstance().addMessage("error", new FacesMessage
                    (result));
        setToAccount(null);
        setAmount(0);
    }
    
    public void showTransactions(){
        transactions = accountStatement.viewTransactions(accountNo);
    }
    
    public void requestChange(){
        String result = resetPassword.reset(accountNo, secAns);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (result));
        setSecAns(null);
    }
    
    public void showQues(){
        Account account = accountFacade.find(accountNo);
        setSecQues(account.getSecques());
    }
    
    public String logout(){
        FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context.
                    getExternalContext().getSession(true);
        session.invalidate();
        accountNo = null;
        return "success";
    }
}

