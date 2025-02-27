using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

using s1411031038_NETFinal.Models;
using s1411031038_NETFinal.Services;
using s1411031038_NETFinal.ViewModels;

namespace s1411031038_NETFinal.Controllers
{
    public class FinalController : Controller
    {
        // GET: Final
        private readonly ImgCarouselDBService ImgCarouselService = new ImgCarouselDBService();

        private readonly ThumbNDBService thumbNService = new ThumbNDBService();
        //宣告Guestbooks資料表的Service物件
        private readonly GuestbooksDBService GuestbookService = new GuestbooksDBService();



        public ActionResult 首頁()
        {
            //宣告一個新頁面模型
            ImgCarouselViewModel Data = new ImgCarouselViewModel();
            //從Service 中取得頁面所需陣列資料
            Data.DataList = ImgCarouselService.GetDataList();
            //將頁面資料傳入View中
            ViewBag.a = "首頁";
            return View(Data);
        }
        public ActionResult 評論區()
        {
            //宣告一個新頁面模型
            GuestbooksViewModel Data = new GuestbooksViewModel();
            //從Service 中取得頁面所需陣列資料
            Data.DataList = GuestbookService.GetDataList();
            //將頁面資料傳入View 中
            return View(Data);
        }
        #region 新增留言
        // 新增留言一開始載入頁面
        public ActionResult Create()
        {
            return PartialView(); // 因為此頁面用於載入至開始頁面中，故使用部分檢視回傳
        }
        // 新增留言傳入資料時的Action
        [HttpPost] // 設定此Action 只接受頁面POST 資料傳入
                   // 使用Bind 的 Include 來定義只接受的欄位，用來避免傳入其他不相干值
        public ActionResult Create([Bind(Include = "name, content")] Guestbooks Data)
        {
            GuestbookService.InsertGuestbooks(Data); // 使用Service 來新增一筆資料
            return RedirectToAction("評論區"); // 重新導向頁面至開始頁面
        }
        #endregion

        #region 修改留言
        // 修改留言頁面要根據傳入編號來決定要修改的資料
        public ActionResult Edit(int id)
        {
            // 取得頁面所需資料，藉由Service 取得
            Guestbooks Data = GuestbookService.GetDataById(id);
            // 將資料傳入View 中
            return View(Data);
        }
        // 修改留言傳入資料時的Action
        [HttpPost] // 設定此Action 只接受頁面POST 資料傳入
                   // 使用Bind 的Inculde 來定義只接受的欄位，用來避免傳入其他不相干值
        public ActionResult Edit(int id, [Bind(Include = "name, content")] Guestbooks UpdateData)
        {
            // 修改資料的是否可修改判斷
            if (GuestbookService.CheckUpdate(id))
            {
                // 將編號設定至修改資料中
                UpdateData.id = id;
                // 使用Service 來修改資料
                GuestbookService.UpdateGuestbooks(UpdateData);
                // 重新導向頁面至開始頁面
                return RedirectToAction("首頁");
            }
            else
            {
                // 重新導向頁面至開始頁面
                return RedirectToAction("首頁");
            }
        }
        #endregion

        #region 回覆留言
        // 回覆留言頁面要根據傳入編號來決定要回覆的資料
        public ActionResult Reply(int id)
        {
            // 取得頁面所需資料，藉由Service 取得
            Guestbooks Data = GuestbookService.GetDataById(id);
            // 將資料傳入View 中
            return View(Data);
        }
        // 修改留言傳入資料時的Action
        [HttpPost] // 設定此Action 只接受頁面POST 資料傳入
                   // 使用Bind 的Inculde 來定義只接受的欄位，用來避免傳入其他不相干值
        public ActionResult Reply(int id, [Bind(Include = "reply,replyTime")] Guestbooks ReplyData)
        {
            // 修改資料的是否可修改判斷
            if (GuestbookService.CheckUpdate(id))
            {
                // 將編號設定至回覆留言資料中
                ReplyData.id = id;
                // 使用Service 來回覆留言資料
                GuestbookService.ReplyGuestbooks(ReplyData);
                // 重新導向頁面至開始頁面
                return RedirectToAction("評論區");
            }
            else
            {
                // 重新導向頁面至開始頁面
                return RedirectToAction("Index");
            }
        }
        #endregion
        #region 刪除留言
        // 刪除頁面要根據傳入編號來刪除資料
        public ActionResult Delete(int id)
        {
            // 使用Service 來刪除資料
            GuestbookService.DeleteGuestbooks(id);
            // 重新導向頁面至開始頁面
            return RedirectToAction("評論區");
        }
        #endregion        
    }
}