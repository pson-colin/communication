package life.senlin.communication.mapper;

import life.senlin.communication.dto.QuestionDTO;
import life.senlin.communication.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author: colin
 * @Date: 15:54 2019/11/8
 */
@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modify,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModify},#{creator},#{tag});")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param("offset") Integer offset, @Param("size")Integer size);

    @Select("select count(1) from question")
    Integer count();
    @Select("select * from question where creator = #{userId} limit #{offset},#{size}")
    List<Question> listByUserId(@Param("userId") Integer userId,@Param("offset") Integer offset, @Param("size")Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from question where id = #{id}")
    Question findById(@Param("id") Integer id);

    @Update("update question set title = #{title}, description = #{description}, gmt_modify = #{gmtModify}, tag = #{tag} where id = #{id}")
    void update(Question question);
}
