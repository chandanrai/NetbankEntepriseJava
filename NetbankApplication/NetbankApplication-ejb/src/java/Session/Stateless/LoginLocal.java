/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Session.Stateless;

import javax.ejb.Local;

/**
 *
 * @author chandan
 */
@Local
public interface LoginLocal {
    
    boolean doLogin(String accountNo, String password);
}