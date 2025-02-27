using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.IO;

using s1411031038_NETFinal.Models;
using s1411031038_NETFinal.Services;
using s1411031038_NETFinal.ViewModels;

namespace s1411031038_NETFinal.Controllers
{
    public class DepEmpController : Controller
    {
        //宣告Guestbooks資料表的Service物件
        private readonly DepEmpDBService DepEmpService = new DepEmpDBService();

        // GET: DepEmp
        public ActionResult 園區動物介紹(int depid = 1)
        {
            //宣告一個新頁面模型
            DepEmpViewModel Data = new DepEmpViewModel();
            //從Service 中取得物種的資料
            Data.depList = DepEmpService.GetDepList();
            //從Service 中取得 "某" 物種的動物資料
            Data.empList = DepEmpService.GetEmpList(depid);
            //從Service 中取得 "某" 物種的名稱
            Data.SrhId = depid;
            Data.SrhName = DepEmpService.GetDepName(depid);
            return View(Data);
        }
        public ActionResult 動物管理(int depid = 1)
        {
            //宣告一個新頁面模型
            DepEmpViewModel Data = new DepEmpViewModel();
            //從Service 中取得物種的資料
            Data.depList = DepEmpService.GetDepList();
            //從Service 中取得 "某" 物種的動物資料
            Data.empList = DepEmpService.GetEmpList(depid);
            //從Service 中取得 "某" 物種的名稱
            Data.SrhId = depid;
            Data.SrhName = DepEmpService.GetDepName(depid);
            return View(Data);
        }
        // 新增動物一開始載入頁面
        public ActionResult Create(int dId)
        {
            // 取得頁面所需資料，藉由Service 取得
            List<Department> depList = new List<Department>();
            ViewBag.depList = DepEmpService.GetDepList();
            ViewBag.dId = dId;
            // 將物種資料傳入View 中
            return View();
        }
        // 新增員工傳入資料時的Action
        [HttpPost] // 設定此Action 只接受頁面POST 資料傳入
                   // 使用Bind 的 Include 來定義只接受的欄位，用來避免傳入其他不相干的值
        public ActionResult Create([Bind(Include = "upload,EmpId, EmpName, EmpImg, DepId")] Employee Data)
        {
            string img = Path.Combine(Server.MapPath("~/images/"), Data.upload.FileName);
            Data.upload.SaveAs(img);
            Data.EmpImg = Data.upload.FileName;
            DepEmpService.InsertEmp(Data); // 使用Service 來新增一筆資料
            return RedirectToAction("動物管理", new { depid = Data.DepId }); // 重新導向頁面至開始頁面
        }
        
        public ActionResult Edit(string eId)
        {
            // 取得頁面所需資料，藉由Service 取得
            Employee Data = DepEmpService.GetEmpById(eId);
            // 將資料傳入View 中
            if (Data == null)
            {
                return RedirectToAction("動物管理");
            }
            else
            {
                return View(Data);
            }
        }
        // 修改員工資料傳入資料時的Action
        [HttpPost] // 設定此Action 只接受頁面POST 資料傳入
                   // 使用Bind 的Inculde 來定義只接受的欄位，用來避免傳入其他不相干值
        public ActionResult Edit(string eId, Employee UpdateData)
        {
            if (UpdateData.upload != null)
            {
                string img = Path.Combine(Server.MapPath("~/images/"), UpdateData.upload.FileName);
                UpdateData.upload.SaveAs(img);
                DepEmpService.UpdateEmp(UpdateData);
            }

            // 將編號設定至修改資料中
            UpdateData.EmpId = eId;
            // 使用Service 來修改資料
           // DepEmpService.UpdateEmp(UpdateData);
            // 重新導向頁面至開始頁面
            return RedirectToAction("動物管理", new { depid = UpdateData.DepId });
        }
        // 刪除頁面要根據傳入編號來刪除資料
        public ActionResult Delete(string eId, int dId)
        {
            // 使用Service 來刪除資料
            DepEmpService.DeleteEmp(eId);
            // 重新導向頁面至開始頁面
            return RedirectToAction("動物管理", new { depid = dId });
        }

        
        
    }
}