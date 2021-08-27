/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.dto;

/**
 *
 * @author Ghazala
 */
public class UserDTO {
    private String userid;
    private String password;

    @Override
    public String toString() {
        return "UserDTO{" + "userid=" + userid + ", password=" + password + '}';
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDTO(String userid, String password) {
        this.userid = userid;
        this.password = password;
    }
    
}
