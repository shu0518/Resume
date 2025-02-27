using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace s1411031038_NETFinal.Models
{
    public class Guestbooks
    {
        //編號
        [DisplayName("編號：")]
        public int id { get; set; }
        //名字
        [DisplayName("標題：")]
        [Required(ErrorMessage = "請輸入名字")]
        [StringLength(20, ErrorMessage = "名字不可超過20 字元")]
        public string name { get; set; }
        //留言內容
        [DisplayName("留言內容：")]
        [Required(ErrorMessage = "請輸入留言內容")]
        [StringLength(100, ErrorMessage = "留言內容不可超過100 字元")]
        public string content { get; set; }
        //新增時間
        [DisplayName("新增時間：")]
        public DateTime createTime { get; set; }
        //回覆內容
        [DisplayName("回覆內容：")]
        [StringLength(100, ErrorMessage = "回覆內容不可超過100 字元")]
        public string reply { get; set; }
        //回覆時間
        //DateTime? 資料型態，允許DateTime有NULL 產生
        [DisplayName("回覆時間：")]
        public DateTime? replyTime { get; set; }
    }
}