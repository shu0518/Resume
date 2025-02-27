using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

using System.Configuration;
using System.Data.SqlClient;
using s1411031038_NETFinal.Models;

namespace s1411031038_NETFinal.Services
{
    public class ThumbNDBService
    {
        //建立與資料庫的連線字串
        private readonly static string cnstr =ConfigurationManager.ConnectionStrings["s1411031038_NETFinal"].ConnectionString;
        //建立與資料庫的連線
        private readonly SqlConnection conn = new SqlConnection(cnstr);
        // 取得陣列資料方法
        public List<ThumbN> GetDataList()
        {
            List<ThumbN> DataList = new List<ThumbN>();
            //Sql 語法
            string sql = @" SELECT * FROM thumbN; ";
            // 確保程式不會因執行錯誤而整個中斷
            try
            {
                conn.Open();                                // 開啟資料庫連線
                SqlCommand cmd = new SqlCommand(sql, conn); // 執行Sql 指令
                SqlDataReader dr = cmd.ExecuteReader();     // 取得Sql 資料
                
                while (dr.Read())                           // 獲得下一筆資料直到沒有資料
                {
                    ThumbN Data = new ThumbN();
                    Data.Tid = Convert.ToInt32(dr["Tid"]);
                    Data.Tname = dr["Tname"].ToString();
                    // 確定此筆資料不為空值
                    if (!dr["Tintroduction"].Equals(DBNull.Value))
                    {
                        Data.Sid = dr
                        ["Sid"].ToString();
                    }
                    if (!dr["Timg"].Equals(DBNull.Value))
                    {
                        Data.Timg = dr
                        ["Timg"].ToString();
                    }
                    if (!dr["Tintroduction"].Equals(DBNull.Value))
                    {
                        Data.Tintroduction = dr
                        ["Tintroduction"].ToString();
                    }

                    DataList.Add(Data);
                }
            }
            catch (Exception e)
            {
                throw new Exception(e.Message.ToString()); // 丟出錯誤
            }
            finally
            {
                conn.Close(); // 關閉資料庫連線
            }
            return DataList
            ;
        }
    }
}