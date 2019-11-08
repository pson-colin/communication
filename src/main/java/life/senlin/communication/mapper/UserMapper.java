package life.senlin.communication.mapper;

import life.senlin.communication.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: colin
 * @Date: 17:07 2019/11/7
 */
@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modify) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModify});")
    void insert(User user);
}
