using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace s1411031038_NETFinal.Models
{
    public class Employee
    {
        //編號
        [DisplayName("動物編號")]
        [Required(ErrorMessage = "請輸入動物編號")]
        [StringLength(10, ErrorMessage = "動物編號不可超過 10 字元")]
        public string EmpId { get; set; }
        //名字
        [DisplayName("動物名字")]
        [Required(ErrorMessage = "請輸入名字")]
        [StringLength(50, ErrorMessage = "名字不可超過 50 字元")]
        public string EmpName { get; set; }
        //電話
        [DisplayName("動物照片")]
        [Required(ErrorMessage = "請上傳動物照片")]
        public string EmpImg { get; set; }
        //所屬部門
        public int DepId { get; set; }

        public HttpPostedFileBase upload { get; set; }

    }
}