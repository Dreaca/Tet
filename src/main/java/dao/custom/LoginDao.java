package dao.custom;

import dao.CrudDao;
import dto.LoginFormDto;

import java.sql.SQLException;

public interface LoginDao extends CrudDao<LoginFormDto> {
     String generateNExtUserID() throws SQLException;
    String splitUserID(String userId);
    boolean authenticate(LoginFormDto login) throws SQLException;
    String getUser(LoginFormDto login) throws SQLException;

    boolean setUpdateUserId(String oldUserId, String newUserId) throws SQLException;
    LoginFormDto getUser(String userName) throws SQLException;

    boolean updateUserName(String userName, String userIdText) throws SQLException;
    boolean checkPass(String userId, String oldPasswordText) throws SQLException;
    boolean updatePassword(String userId, String newpW) throws SQLException;
}
