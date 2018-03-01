package com.lakefarm.service.Impl;

import com.lakefarm.mapper.RecordMapper;
import com.lakefarm.pojo.Record;
import com.lakefarm.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rxl on 17-6-6.
 */
@Service
public class RecordServiceImp implements RecordService {
    @Autowired
    private RecordMapper recordMapper;
    @Override
    public int addRecord(Record t) {
        return recordMapper.addRecord(t);
    }

    @Override
    public int deleteRecord(String record_id) {
        return recordMapper.deleteRecord(record_id);
    }

    @Override
    public int updateRecord(Record t) {
        return recordMapper.updateRecord(t);
    }

    @Override
    public List<Record> getRecordById(String u_id) {
        return recordMapper.getRecordById(u_id);
    }
}
