package com.example.dropoflife.Classes;

/**
 * @author Bashar
 */
public class Roles {
    public  static String [] roles = {"user","admin","hospitalWorker"};
    private String role;
    private int ID;
    public Roles(){/*ignore this Constructor it is for the database */}
    public Roles(int ID) throws IncorrectRoleExciption {
            if(ID>=0&&ID<=2)
        role=roles[ID];
            else
                throw new IncorrectRoleExciption("Incorrect Role");
    }

    /**
     *
     * @param role it set the role cant be used by the programmer it is to be used by the firebase database only
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     *
     * @param ID For the Firebase database Only
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    public String getRole() {
        return role;
    }

    public int getID() {
        return ID;
    }

    public class IncorrectRoleExciption extends Exception {
        public IncorrectRoleExciption(String e) {
            super(e);
        }
    }

}
