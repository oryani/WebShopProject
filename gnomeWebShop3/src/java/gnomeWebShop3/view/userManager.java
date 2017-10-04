package gnomeWebShop3.view;

import gnomeWebShop3.controller.UserFacade;
import gnomeWebShop3.model.Gnome;
import gnomeWebShop3.model.Shopping;
import gnomeWebShop3.model.User1;
import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;

@Named(value = "userManager")
@ConversationScoped
public class userManager implements Serializable {
    private static final long serialVersionUID = 16247164405L;
    /** Creates a new instance of userManager */
    @EJB
    private UserFacade facade;
    private String userName;
    private String password;
    private String regUserName;
    private String regPassword;
    private boolean loginSuccessful=false;
    private boolean userRegistrationSuccessful=false;
    private boolean registrationError=false;
    private boolean loginError=false;
    private boolean displayItemTrue=false;
    private boolean displayShoppingTrue =false;
    private List<Gnome> list;
    private List<Shopping> shoppingList;
    private String selectedGnome;
    private int selectedGnomeUnit = 1;
    private boolean displayShoppingError = false;
    private boolean displayPayError = false;
    private boolean displayPaySuccess = false;
    private boolean loginAdminSuccess = false;
    private String newGnomeName;
    private double newGnomePrice;
    private int newGnomeUnit;
    private boolean newgnomeAddedSuccess = false;
    private boolean newgnomeAddedError = false;
    private boolean deleteInventorySuccess = false;
    private String updateSelectedGnome;
    private int updateSelectedGnomeUnit =1;
    private boolean updateInventoryUnitSuccess = false;
    private String baneSelectedUser;
    private boolean userBanedSuccess = false;
    private List<User1>listUser;
    private boolean userBannedTrue = false;
    
    @Inject
    private Conversation conversation;

    private void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }
    
    private void stopConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }
    
    public void displayUsers()
    {
        try {
            startConversation();
            this.listUser = facade.displayUsers();
            
            
        } catch (Exception e) {
            handleException(e);
        }
    }
    public void banUser()
    {
        try {
            startConversation();
            boolean worked = facade.banUser(baneSelectedUser);
            if(worked)
            {
                this.userBanedSuccess = true;
                
            }
            
        } catch (Exception e) {
            handleException(e);
        }
    }
    public void updateUnitInInventory()
    {
        try {
            startConversation();
            boolean worked = facade.updateUnitInInventory(updateSelectedGnome, updateSelectedGnomeUnit);
            if(worked)
            {
                this.updateInventoryUnitSuccess = true;
                displayItems();
                
            }
            
        } catch (Exception e) {
            handleException(e);
        }
    }
    public void deleteFromInventory()
    {
       try {
            startConversation();
            boolean worked = facade.deleteFromInventory(selectedGnome);
            if(worked)
            {
                this.deleteInventorySuccess = true;
                
            }
                
            
            displayItems();
            
        } catch (Exception e) {
            handleException(e);
        }
    }
    public void addToInventory()
    {
        try {
            startConversation();
            boolean worked = facade.addToInventory(newGnomeName, newGnomePrice, newGnomeUnit);
            if(worked)
            {
                
                this.newgnomeAddedSuccess = true;
            }
            else
            {
                
                this.newgnomeAddedError = true;
            }
            this.displayItemTrue = true;
            this.newGnomeName ="";
            this.newGnomePrice = 0.0;
            this.newGnomeUnit = 1;
            displayItems();
            
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    private void setAllvariabletoFalse()
    {
        
        userBannedTrue = false;
        registrationError=false;
        loginError=false;
        displayShoppingError = false;
        displayPayError = false;
        displayPaySuccess = false;
        displayPaySuccess = false;
        displayPayError = false;
        newgnomeAddedSuccess = false;
        newgnomeAddedError = false;
        deleteInventorySuccess = false;
        updateInventoryUnitSuccess = false;
    }
    public void setDisplayShoppingError(boolean err)
    {
        this.displayShoppingError = err;
    }
    
    public boolean getDisplayShoppingError()
    {
        return this.displayShoppingError;
    }
    private void handleException(Exception e) {
        stopConversation();
        e.printStackTrace(System.err);
        //transactionFailure = e;
    }
    
    
    public void displayItems()
    {
        
        
        try {
            startConversation();
            list = facade.displayItems();
            this.displayItemTrue = true;
            
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    public void displayShopping()
    {
        this.displayPayError = false;
        this.displayPaySuccess = false;
        try {
            startConversation();
            shoppingList = facade.displayShopping(this.userName);
            if(shoppingList.size()>0)
            {
                
                this.displayShoppingTrue = true;
            }
            
        } catch (Exception e) {
            handleException(e);
        }
        
    }
    
    
    public void addToShopping()
    {
        this.displayPayError = false;
        this.displayPaySuccess = false;
        try {
            //loginSuccessful = true;
            startConversation();
           boolean worked = facade.addShopping(this.userName, this.selectedGnome, this.selectedGnomeUnit);
           if(!worked)
           {
               
               this.displayShoppingError=true;
           }
           else
           {
               
              this.displayShoppingError=false;
             displayShopping();  
           }
            } catch (Exception e) {
            handleException(e);
        }
    }
    
    
    public void payShopping()
    {
        this.displayPayError = false;
        this.displayPaySuccess = false;
        try {
            startConversation();
           boolean payWorked = facade.payShopping(this.userName);
           if(!payWorked)
           {
              
               this.displayPayError=true; 
           }
           else
           {
              
               this.displayPaySuccess=true;
              this.displayShoppingTrue = false;
           }
            } catch (Exception e) {
            handleException(e);
        }
    }
    public void createGnome()
    {
        try {
            startConversation();
            facade.CreateGnome("gnome4", 12.5, 10);
            } catch (Exception e) {
            handleException(e);
        }
    }
    
    public void Login()
    {
        this.loginError = false;
        this.userBannedTrue =false;
        try {
            startConversation();
            User1 user = facade.Login(this.userName, this.password);
            if(user==null)
            {
                this.loginError = true;
                this.loginSuccessful =false;
            }
            else if(user.getIsBlocked()==true)
            {
                this.userBannedTrue = true;
            }
            else
            {
                if(user.getIsAdmin()==true)
                {
                    this.loginAdminSuccess= true;
                    displayUsers();
                }
                else
                    this.loginSuccessful = true;
                    
            }
            //this.loginSuccessful = facade.Login(this.userName, this.password);
            /*if(!this.loginSuccessful)
                this.loginError=true;
            else
                this.loginError = false;
             * 
             */
            
        } catch (Exception e) {
            handleException(e);
        }
    }
    public void LogOut()
    {
        this.loginSuccessful = false;
        this.displayItemTrue =false;
        this.displayShoppingTrue =false;
        this.loginAdminSuccess = false;
        setAllvariabletoFalse();
        
    }
    
    public void UserRegister()
    {
        try {
            startConversation();
            this.userRegistrationSuccessful = facade.RegisterUSer(this.regUserName, this.regPassword);
            if(!this.userRegistrationSuccessful)
            {
               
                registrationError = true;
            }
            else
                registrationError = false;
            } catch (Exception e) {
            handleException(e);
        }
    }
    
   
    
    public void setUserName(String username)
    {
        this.userName = username;
    }
    
    public String getUserName()
    {
        return this.userName;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return this.password;
    }
    public void setLoginSuccessful(boolean log)
    {
        this.loginSuccessful = log;
    }
    
    public boolean getLoginSuccessful()
    {
        return this.loginSuccessful;
    }

    public void setUserRegistrationSuccessful(boolean regi)
    {
        this.userRegistrationSuccessful = regi;
    }
    public boolean getUserRegistrationSuccessful()
    {
        return this.userRegistrationSuccessful;
    }
    
    public void setRegUserName(String username)
    {
        this.regUserName = username;
    }
    public String getRegUserName()
    {
        return this.regUserName;
    }
    
    public void setRegPassword(String password)
    {
        this.regPassword = password;
    }
    public String getRegPassword()
    {
        return this.regPassword;
    }
    public void setSelectedGnome(String selected)
    {
        this.selectedGnome = selected;
    }
    
    public String getSelectedGnome()
    {
        return this.selectedGnome;
    }
    
    public boolean getDisplayItemTrue()
    {
        return this.displayItemTrue;
    }
    public List<Gnome> getList()
    {
        return this.list;
    }
    
    public void setList(List<Gnome> gn)
    {
        this.list = gn;
    }
    public void setRegistrationError(boolean regErr)
    {
        this.registrationError = regErr;
    }
    public boolean getRegistrationError()
    {
        return this.registrationError;
    }
    public void setLoginError(boolean logErr)
    {
        this.loginError = logErr;
    }
    public boolean getLoginError()
    {
        return this.loginError;
    }
    public void setDisplayShoppingTrue(boolean shop)
    {
        this.displayShoppingTrue = shop;
    }
    public boolean getDisplayShoppingTrue()
    {
        return this.displayShoppingTrue;
    }
    
    public void setShoppingList(List<Shopping> shop)
    {
        this.shoppingList = shop;
    }
    public List<Shopping> getShoppingList()
    {
        return this.shoppingList;
    }
    
    public void setSelectedGnomeUnit(int unit)
    {
        this.selectedGnomeUnit = unit;
    }
    public int getSelectedGnomeUnit()
    {
        return this.selectedGnomeUnit;
    }
    public boolean getDisplayPayError()
    {
        return this.displayPayError;
    }
    public void setDisplayPayError(boolean pay)
    {
        this.displayPayError = pay;
    }
    
    public boolean getDisplayPaySuccess()
    {
        return this.displayPaySuccess;
    }
    public void setdisplayPaySuccess(boolean pay)
    {
        this.displayPaySuccess = pay;
    }
    public void setLoginAdminSuccess(boolean suc)
    {
        this.loginAdminSuccess =suc;
    }
    public boolean getLoginAdminSuccess()
    {
        return this.loginAdminSuccess;
    }
    
    public int getNewGnomeUnit()
    {
        return this.newGnomeUnit;
    }
    public void setNewGnomeUnit(int unit)
    {
        this.newGnomeUnit = unit;
    }
    public double getNewGnomePrice()
    {
        return this.newGnomePrice;
    }
    public void setNewGnomePrice(double price)
    {
        this.newGnomePrice = price;
    }
    public String getNewGnomeName()
    {
        return this.newGnomeName;
    }
    public void setNewGnomeName(String name)
    {
        this.newGnomeName = name;
    }
    public void setNewgnomeAddedSuccess(boolean suc)
    {
        this.newgnomeAddedSuccess =suc;
    }
    public boolean getNewgnomeAddedSuccess()
    {
        return this.newgnomeAddedSuccess;
    }
    public void setNewgnomeAddedError(boolean suc)
    {
        this.newgnomeAddedError =suc;
    }
    public boolean getNewgnomeAddedError()
    {
        return this.newgnomeAddedError;
    }
    public boolean getUpdateInventoryUnitSuccess()
    {
        return this.updateInventoryUnitSuccess;
    }
    public void setUpdateInventoryUnitSuccess(boolean suc)
    {
        this.updateInventoryUnitSuccess = suc;
    }
    public int getUpdateSelectedGnomeUnit()
    {
        return this.updateSelectedGnomeUnit;
    }
    public void setUpdateSelectedGnomeUnit(int unit)
    {
        this.updateSelectedGnomeUnit = unit;
    }
    public String getUpdateSelectedGnome()
    {
        return this.updateSelectedGnome;
    }
    public void setUpdateSelectedGnome(String name)
    {
        this.updateSelectedGnome = name;
    }
    
    public void setDeleteInventorySuccess(boolean suc)
    {
        this.deleteInventorySuccess = suc;
    }
    public boolean getDeleteInventorySuccess()
    {
        return this.deleteInventorySuccess;
    }
    public void setUserBanedSuccess(boolean suc)
    {
       this.userBanedSuccess = suc; 
    }
    public boolean getUserBanedSuccess()
    {
        return this.userBanedSuccess;
    }
    public void setBaneSelectedUser(String ban)
    {
        this.baneSelectedUser = ban;
    }
    public String getBaneSelectedUser()
    {
        return this.baneSelectedUser;
    }
    public boolean getUserBannedTrue()
    {
        return this.userBannedTrue;
    }
    public void setUserBannedTrue(boolean ban)
    {
        this.userBannedTrue = ban;
    }
    public void setListUser(List<User1> usr)
    {
        this.listUser = usr;
    }
    public List<User1> getListUser()
    {
        return this.listUser;
    }
    
}
