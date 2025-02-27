using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

using System.Configuration;
using System.Data.SqlClient;
using s1411031038_NETFinal.Models;

namespace s1411031038_NETFinal.Services
{
    public class DepEmpDBService
    {
        //建立與資料庫的連線字串
        private readonly static string cnstr =
        ConfigurationManager.ConnectionStrings["s1411031038_NETFinal"].ConnectionString;
        //建立與資料庫的連線
        private readonly SqlConnection conn = new SqlConnection(cnstr);

        public List<Department> GetDepList()
        {
            List<Department> depList = new List<Department>();
            string sql = $@" SELECT * FROM Dep;";
            try
            {
                conn.Open(); // 開啟資料庫連線
                SqlCommand cmd = new SqlCommand(sql, conn); // 執行Sql 指令
                SqlDataReader dr = cmd.ExecuteReader(); // 取得Sql 資料
                while (dr.Read()) // 獲得下一筆資料直到沒有資料
                {
                    Department Data = new Department();
                    Data.DepId = Convert.ToInt32(dr["DepId"]);
                    Data.DepName = dr["DepName"].ToString();
                    Data.DepId = Convert.ToInt32(dr["DepId"]);
                    depList.Add(Data);
                } // while結束
            }
            catch (Exception e)
            {
                throw new Exception(e.Message.ToString()); // 丟出錯誤
            }
            finally
            {
                conn.Close(); // 關閉資料庫連線
            }
            return depList;
        }
        public List<Employee> GetEmpList(int dId)
        {
            List<Employee> empList = new List<Employee>();
            string sql = $@" SELECT * FROM Emp WHERE DepId = {dId};";
            try
            {
                conn.Open(); // 開啟資料庫連線
                SqlCommand cmd = new SqlCommand(sql, conn); // 執行Sql 指令
                SqlDataReader dr = cmd.ExecuteReader(); // 取得Sql 資料
                while (dr.Read()) // 獲得下一筆資料直到沒有資料
                {
                    Employee Data = new Employee();
                    Data.EmpId = dr["EmpId"].ToString();
                    Data.EmpName = dr["EmpName"].ToString();
                    Data.EmpImg = dr["EmpImg"].ToString();
                    empList.Add(Data);
                } // while結束
            }
            catch (Exception e)
            {
                throw new Exception(e.Message.ToString()); // 丟出錯誤
            }
            finally
            {
                conn.Close(); // 關閉資料庫連線
            }
            return empList;
        }
        public string GetDepName(int dId)
        {
            string depN = "";
            string sql = $@" SELECT DepName FROM Dep WHERE DepId = {dId};";
            try
            {
                conn.Open(); // 開啟資料庫連線
                SqlCommand cmd = new SqlCommand(sql, conn); // 執行Sql 指令
                SqlDataReader dr = cmd.ExecuteReader(); // 取得Sql 資料
                while (dr.Read()) // 獲得下一筆資料直到沒有資料
                {
                    depN = dr["DepName"].ToString();
                } // while結束
            }
            catch (Exception e)
            {
                throw new Exception(e.Message.ToString()); // 丟出錯誤
            }
            finally
            {
                conn.Close(); // 關閉資料庫連線
            }
            return depN;
        }
        public void InsertEmp(Employee newData)
        {
            //Sql 新增語法
            string sql = $@" INSERT INTO Emp(EmpId,EmpName,EmpImg,DepId)
                VALUES ( '{newData.EmpId}','{newData.EmpName}','{newData.EmpImg}','{newData.DepId}' ) ";
            // 確保程式不會因執行錯誤而整個中斷
            try
            {
                // 開啟資料庫連線
                conn.Open();
                // 執行Sql 指令
                SqlCommand cmd = new SqlCommand(sql, conn);
                cmd.ExecuteNonQuery();
            }
            catch (Exception e)
            {
                throw new Exception(e.Message.ToString()); // 丟出錯誤
            }
            finally
            {
                conn.Close(); // 關閉資料庫連線
            }
        }
        public Employee GetEmpById(string eId)
        {
            Employee Data = new Employee();
            string sql = $@" SELECT * FROM Emp WHERE EmpId = '{eId}'; "; //Sql 語法
                                                                         // 確保程式不會因執行錯誤而整個中斷
            try
            {
                conn.Open(); // 開啟資料庫連線
                SqlCommand cmd = new SqlCommand
                (sql, conn); // 執行Sql 指令
                SqlDataReader dr = cmd.ExecuteReader(); // 取得Sql 資料
                dr.Read();
                Data.EmpId = dr["EmpId"].ToString();
                Data.EmpName = dr["EmpName"].ToString();
                Data.EmpImg = dr["EmpImg"].ToString();
                Data.DepId = Convert.ToInt32(dr["DepId"]);
            }
            catch (Exception e)
            {
                Data = null; // 查無資料
            }
            finally
            {
                conn.Close(); // 關閉資料庫連線
            }
            // 回傳根據編號所取得的資料
            return Data
            ;
        }
        public void UpdateEmp(Employee UpdateData)
        { //Sql 修改語法
            string sql = $@" UPDATE Emp SET EmpName = '{ UpdateData.EmpName}',EmpImg = '{UpdateData.upload.FileName}', DepId = { UpdateData.DepId}
                             where EmpId = '{UpdateData.EmpId}' ";
            try // 確保程式不會因執行錯誤而整個中斷
            {
                conn.Open(); // 開啟資料庫連線
                SqlCommand cmd = new SqlCommand(sql, conn); // 執行Sql 指令
                cmd.ExecuteNonQuery();
            }
            catch (Exception e)
            {
                throw new Exception(e.Message.ToString()); // 丟出錯誤
            }
            finally
            {
                conn.Close(); // 關閉資料庫連線
            }
        }
        public void DeleteEmp(string Id)
        {
            //Sql 刪除語法
            // 根據Id 取得要刪除的資料
            string sql = $@" DELETE FROM Emp WHERE EmpId = '{Id}'; ";
            // 確保程式不會因執行錯誤而整個中斷
            try
            {
                conn.Open(); // 開啟資料庫連線
                SqlCommand cmd = new SqlCommand(sql, conn); // 執行Sql 指令
                cmd.ExecuteNonQuery();
            }
            catch (Exception e)
            {
                throw new Exception(e.Message.ToString() + "Id=" + Id); // 丟出錯誤
            }
            finally
            {
                conn.Close(); // 關閉資料庫連線
            }
        }
    }
}