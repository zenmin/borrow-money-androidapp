package com.zm.borrowmoneyandriodapp.service;

import com.zm.borrowmoneyandriodapp.entity.DO.Pager;
import com.zm.borrowmoneyandriodapp.entity.BorrowHistory;
import java.util.List;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-08-25 10:31:17
* https://github.com/zenmin/ProjectTemplate
*/

public interface BorrowHistoryService {

    BorrowHistory getOne(Long id);

    List<BorrowHistory> list(BorrowHistory borrow_history);

    Pager listByPage(Pager pager,BorrowHistory borrow_history);

    BorrowHistory save(BorrowHistory borrow_history);

    boolean delete(String ids);

    BorrowHistory saveMyInfo(BorrowHistory borrow_history);

}
