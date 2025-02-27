using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace s1411031038_NETFinal.Models
{
    public class Department
    {
        //編號
        [DisplayName("物種編號")]
        public int DepId { get; set; }
        //名稱
        [DisplayName("物種名稱")]
        [Required(ErrorMessage = "請輸入物種名稱")]
        [StringLength(50, ErrorMessage = "名稱不可超過50 字元")]
        public string DepName { get; set; }
    }
}