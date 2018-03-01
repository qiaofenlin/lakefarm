package com.lakefarm.service;

import com.lakefarm.pojo.Record;

import java.util.List;

/**
 * Created by rxl on 17-6-6.
 */
public interface RecordService {
    public int addRecord(Record t);

    public int deleteRecord(String record_id);

    public int updateRecord(Record t);

    public List<Record> getRecordById(String u_id);
}
