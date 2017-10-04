package gnomeWebShop3.model;

public interface User1DTO {
    String getUserName();
    String getPassword();
    boolean getIsAdmin();
    boolean getIsBlocked();
    
}

/*
 
 <navigation-rule>
       <from-view-id>/startpage.xhtml</from-view-id>
       <navigation-case>
           <if>#{userManager.loginSuccessful}</if>
           <to-view-id>/mainpage.xhtml</to-view-id>
           
       </navigation-case>
       <navigation-case>
           <if>#{!userManager.loginSuccessful}</if>
           <to-view-id>/error.xhtml</to-view-id>
       </navigation-case>
       
   </navigation-rule>
 * 
 * 
 * <navigation-rule>
       <from-view-id>/register.xhtml</from-view-id>
       <navigation-case>
           <if>#{userManager.userRegistrationSuccessful}</if>
           <to-view-id>/mainpage.xhtml</to-view-id>
           
       </navigation-case>
       
   </navigation-rule>
 */
