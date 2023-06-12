package com.example.appdatdoan.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DetailCartDAO {
    @Insert
    void insertDetailCart(DetailCart detailCart);
    @Query("SELECT * from detailcart1 where IdUser = :Id_User")
    List<DetailCart> getListDetailCart(String Id_User);
    //chon ra nhwung ban ghi da ton tai truoc do
    @Update
    void updateUser(DetailCart detailCart);
    @Delete
    void deleteUser(DetailCart detailCart);
   @Query("DELETE from detailcart1 where IdUser = :Id_User")
    void deleteAllDetailCart(String Id_User);

}
