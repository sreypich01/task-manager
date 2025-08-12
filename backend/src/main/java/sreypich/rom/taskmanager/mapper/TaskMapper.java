package sreypich.rom.taskmanager.mapper;

import sreypich.rom.taskmanager.model.Task;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface TaskMapper {
    
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "title", column = "title"),
        @Result(property = "description", column = "description"),
        @Result(property = "status", column = "status"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at")
    })
    @Select("SELECT * FROM tasks")
    List<Task> findAll();

    @Insert("INSERT INTO tasks(title, description, status, created_at, updated_at) " +
            "VALUES(#{title}, #{description}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Task task);

    @Update("UPDATE tasks SET title=#{title}, description=#{description}, " +
            "status=#{status}, updated_at=NOW() WHERE id=#{id}")
    void update(Task task);

    @Delete("DELETE FROM tasks WHERE id = #{id}")
    void delete(Long id);

    @Results({
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at")
    })
    @Select("SELECT * FROM tasks WHERE id = #{id}")
    Task findById(Long id);
}