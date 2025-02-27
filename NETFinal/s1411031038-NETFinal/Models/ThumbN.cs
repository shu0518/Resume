using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace s1411031038_NETFinal.Models
{
    public class ThumbN
    {
        [DisplayName("編號")]
        public int Tid { get; set; }
        [DisplayName("名字")]
        public string Tname { get; set; }
        [DisplayName("物種")]
        public string Sid { get; set; }
        [DisplayName("照片")]
        public string Timg { get; set; }
        [DisplayName("簡介")]
        public string Tintroduction { get; set; }
    }
}