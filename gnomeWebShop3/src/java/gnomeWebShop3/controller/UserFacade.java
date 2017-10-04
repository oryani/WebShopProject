package gnomeWebShop3.controller;

import gnomeWebShop3.model.Gnome;
import gnomeWebShop3.model.GnomeDTO;
import gnomeWebShop3.model.Shopping;
import gnomeWebShop3.model.User1;
import gnomeWebShop3.model.User1DTO;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
//@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class UserFacade {
    @PersistenceContext(unitName = "gnomeWebShop3PU")
    private EntityManager em, em2;
    
    
    public List<User1> displayUsers()
    {
        List<User1> users=null;
        
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("gnomeWebShop3PU");
        em=emf.createEntityManager();
        
        try{
            Query query = em.createNamedQuery("findAllUsers");
            users = query.getResultList();
            
            if(users.size() >0)
            {
                return users;
            }
            
         }finally
         {
            //em.close(); 
         }  
        
        return users;
    }
    
    public boolean banUser(String userName)
    {
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("gnomeWebShop3PU");
        em=emf.createEntityManager();
        List<User1> foundUser;
        try{
            Query query = em.createNamedQuery("findUserByUserName");
            query.setParameter("username", userName);
            
            foundUser = query.getResultList();
            
            if(foundUser.size() ==1)
            {
                foundUser.get(0).blockUser();
                return true;
            }
            
         }finally
         {
            //em.close(); 
         }  
        
        
        
        return false;
    }
    
    public boolean updateUnitInInventory(String gnomeName, int gnomeUnit)
    {
        Gnome gnomeFound =null;
        gnomeFound = em2.find(Gnome.class, gnomeName);
        gnomeFound.updateGnomeUnit(gnomeUnit, 1);
        return true;
    }
    
    public boolean deleteFromInventory(String gnomeName)
    {
        Gnome gnomeFound =null, newgnome;
        gnomeFound = em2.find(Gnome.class, gnomeName);
        em2.remove(gnomeFound);
        return true;
    }
    public boolean addToInventory(String ngName, double ngPrice, int ngUnit)
    {
        // if found with this name, return false
        Gnome gnomeFound =null, newgnome;
        gnomeFound = em2.find(Gnome.class, ngName);
        if(gnomeFound != null)
            return false;
        newgnome = new Gnome(ngName, ngPrice, ngUnit);
        em2.persist(newgnome);
        return true;
    }
    public User1 Login(String username, String pass)
    {
         
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("gnomeWebShop3PU");
        em=emf.createEntityManager();
        List<User1> foundUser;
        try{
            Query query = em.createNamedQuery("userLogin");
            query.setParameter("username", username);
            query.setParameter("password", pass);
            foundUser = query.getResultList();
            
            if(foundUser.size() ==1)
                return foundUser.get(0);
                //return true;
         }finally
         {
            em.close(); 
         }  
        return null;
    }
    
    public boolean payShopping(String userName)
    {
        
        //em=emf.createEntityManager();
        Gnome gnome;
        try{
            //decrease from inventory 
            List<Shopping> shopping = displayShopping(userName);
            //first check all requested are available in the inventory
            for(int i=0; i< shopping.size(); i++)
            {
                gnome = em2.find(Gnome.class, shopping.get(i).getGnomeName()); 
                if(gnome.getGnomeUnit() < shopping.get(i).getGnomeUnit())
                    return false;
            }
            
            for(int i=0; i< shopping.size(); i++)
            {
                gnome = em2.find(Gnome.class, shopping.get(i).getGnomeName());
                gnome.updateGnomeUnit(shopping.get(i).getGnomeUnit(), 0);
            }
            //delete from shopping
            EntityManagerFactory emf=Persistence.createEntityManagerFactory("gnomeWebShop3PU");
             em=emf.createEntityManager();
            Query query = em.createNamedQuery("payShopping");
            query.setParameter("username", userName);
            int row = query.executeUpdate();
            if(row > 0)
                
                return true;
             else
                return false;
        }finally
        {

        }

    }
    public boolean addShopping(String userName, String gnomeName, int gnomeUnit )
    {
        //firs check the avilable quantity
         EntityManagerFactory emf=Persistence.createEntityManagerFactory("gnomeWebShop3PU");
        em=emf.createEntityManager();
        List<Gnome> foundGnome;
        int totalgnomeUnit = 0;
        try{
            
            Gnome gnomefound = em2.find(Gnome.class, gnomeName);
            totalgnomeUnit = gnomefound.getGnomeUnit();
            if( totalgnomeUnit< gnomeUnit)
                return false;
         }finally
         {
            //em.close(); 
         } 
        
        //first check in the shopping list if it is already registered
        
       List<Shopping> foundShopping;
        try{
            Query query = em.createNamedQuery("findShopping");
            query.setParameter("gnomename", gnomeName);
            query.setParameter("username", userName);
            foundShopping = query.getResultList();
            if(foundShopping.size()==1)
            {
                int gnomeId = foundShopping.get(0).getId();
                Shopping shop = em2.find(Shopping.class, gnomeId);
                //check here also the quantity
                if(totalgnomeUnit < gnomeUnit + shop.getGnomeUnit())
                    return false;
                shop.updateUnit(gnomeUnit);
                return true;
            }
         }finally
         {
            //em2.close(); 
         } 
       
        int id = getId("Shopping");
        inserShopping(id, userName, gnomeName, gnomeUnit);
        
        return true;
    }
    
    public List<Shopping> displayShopping(String username)
    {
        List<Shopping> shoppingList = null;
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("gnomeWebShop3PU");
        em=emf.createEntityManager();
        try
        {
            Query query = em.createNamedQuery("listShopping");
            query.setParameter("username", username);
            shoppingList = query.getResultList();
            
        }finally
         {
            em.close(); 
         }  
        
        return shoppingList;
        
    }
    public List<Gnome> displayItems()
    {
        List<Gnome> items = null;
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("gnomeWebShop3PU");
        em=emf.createEntityManager();
        try
        {
            Query query = em.createNamedQuery("listGnomes");
            items = query.getResultList();
            
        }finally
         {
            em.close(); 
         }  
        
        return items;
        
    }
    
    public void CreateGnome(String name, double price, int unit)
    {
      Gnome newGnome = new Gnome(name, price, unit);
      em.persist(newGnome);
      
    }
    
    public boolean RegisterUSer(String username, String pass)
    {
        //first check the username is not available
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("gnomeWebShop3PU");
        em=emf.createEntityManager();
        List<User1DTO> foundUser;
        try{
            Query query = em.createNamedQuery("findUserByUserName");
            query.setParameter("username", username);
            foundUser = query.getResultList();
            if(foundUser.size() !=0)
                return false;
         }finally
         {
            //em.close(); 
         } 
        
        //get maximum id;
        int idd = getId("User1");
        insertUser(idd, username, pass);
        //em.getTransaction().commit();
        return true;
    }
    
     public void insertUser(int id, String userName, String password){
        Query query = em.createNativeQuery("INSERT INTO USER1 (id, username, password, isadmin, isblocked) " +
            " VALUES(?,?,?,?,?)");
        query.setParameter(1, id);
        query.setParameter(2, userName);
        query.setParameter(3, password);
        query.setParameter(4, 0);
        query.setParameter(5, 0);
        query.executeUpdate();
    }
     
     
     public void inserShopping(int id, String uName, String gName, int gUnit)
     {
         Query query = em.createNativeQuery("INSERT INTO Shopping (id, userName, gnomeName, gnomeUnit) " +
            " VALUES(?,?,?,?)");
        query.setParameter(1, id);
        query.setParameter(2, uName);
        query.setParameter(3, gName);
        query.setParameter(4, gUnit);
        query.executeUpdate();
     }
     public int getId(String tableName)
     {
         int id =0;
         List<Integer> x;
         Query query = em.createNativeQuery("select MAX(id) as id from "+ tableName);
         x = query.getResultList();
         return x.get(0) + 1;
     }
    
}
