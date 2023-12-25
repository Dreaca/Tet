package dao.custom;

import dao.CrudDao;
import dto.ScheduleDto;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.List;

public interface ScheduleDao extends CrudDao<ScheduleDto> {

    String getVetSheduleData(String sheduleId) throws SQLException;
    String getNextShedId() throws SQLException;
    String generate(String string);
}
