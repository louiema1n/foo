package com.lm.mapper;

import com.lm.domain.shiro.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * Created by Louie on 2017-08-21.
 */
public interface UserMapper {

    @Select("select * from user where issch = 1")
    List<User> selectAllSch();

    @Select("select * from user")
    List<User> selectAll();

    @Select("select * from user where uid = #{uid}")
    User selectById(long uid);

    @Select(("select * from user where username = #{username}"))
    User findByUsername(String username);

    @UpdateProvider(type = UserDaoProvider.class, method = "updByUser")
    Integer updAccountByUser(User user);

    // 动态sql拼接
    class UserDaoProvider {
        public String updByUser(User user) {
            String sql = "update user set ";
            if (user.getUsername() != null) {
                sql += "username = '" + user.getUsername() + "', ";
            }
            if (user.getNickname() != null) {
                sql += "nickname = '" + user.getNickname() + "', ";
            }
            if (user.getPassword() != null) {
                sql += "password = '" + user.getPassword() + "', ";
            }
            if (user.getSalt() != null) {
                sql += "salt = '" + user.getSalt() + "', ";
            }
            if (user.getState() != null) {
                sql += "state = " + user.getState() + ", ";
            }
            if (user.getEmail() != null) {
                sql += "email = '" + user.getEmail() + "', ";
            }
            // 拼接issch
            sql += "issch = " + user.getIssch() + ", ";
            sql = sql.substring(0, sql.lastIndexOf(", "));

            // 判断是否有uid
            if (user.getUid() != 0) {
                sql += " where uid = " + user.getUid();
            } else {
                sql += " where username = '" + user.getUsername() + "'";
            }
            return sql;
        }
    }

}
