package life.senlin.communication.mapper;

import life.senlin.communication.model.User;
import org.apache.ibatis.annotations.*;

/**
 * @Author: colin
 * @Date: 17:07 2019/11/7
 */
@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modify,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModify},#{avatarUrl});")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name = #{name}, token = #{token}, gmt_modify = #{gmtModify}, avatar_url = #{avatarUrl} where id = #{id}")
    void update(User dbUser);
}
