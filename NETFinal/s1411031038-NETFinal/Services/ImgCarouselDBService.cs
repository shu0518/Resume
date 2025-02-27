using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Configuration;
using System.Data.SqlClient;
using s1411031038_NETFinal.Models;

namespace s1411031038_NETFinal.Services
{
    public class ImgCarouselDBService
    {
        //建立與資料庫的連線字串
        private readonly static string cnstr = ConfigurationManager.ConnectionStrings["s1411031038_NETFinal"].ConnectionString;
        //建立與資料庫連線
        private readonly SqlConnection conn = new SqlConnection(cnstr);
        // 取得陣列資料方法
        public List<ImgCarousel> GetDataList()
        {
            List<ImgCarousel> DataList = new List<ImgCarousel>();
            //Sql 語法
            string sql = @" SELECT * FROM ImgCarousel; ";
            // 使用 try 確保程式不會因執行錯誤而整個中斷
            try
            {
                conn.Open(); // 開啟資料庫連線
                SqlCommand cmd = new SqlCommand(sql, conn); // 執行Sql 指令
                SqlDataReader dr = cmd.ExecuteReader(); // 取得Sql 資料
                while (dr.Read()) // 獲得下一筆資料直到沒有資料
                {
                    ImgCarousel Data = new ImgCarousel();
                    Data.pid = Convert.ToInt32(dr["pid"]);
                    Data.pfile = dr["pfile"].ToString();
                    // 資料庫允許空值的欄位先確定資料不為空值再轉換
                    //因C# 是強型別語言，所以轉換時不允許存取 null
                    if (!dr["ptitle"].Equals(DBNull.Value))
                    {
                         Data.ptitle = dr["ptitle"].ToString();
                    }
                    if (!dr["pinfo"].Equals(DBNull.Value))
                    {
                        Data.pinfo = dr["pinfo"].ToString();
                    }
                     DataList.Add(Data);
                }
            }
            catch (Exception e)
            { // 丟出錯誤
              throw new Exception(e.Message.ToString());
            }
            finally
            {
                conn.Close(); // 關閉資料庫連線
            }
            return DataList;
            }
        }
}