(window.webpackJsonp=window.webpackJsonp||[]).push([["chunk-4c7c"],{"4zEA":function(e,t,a){"use strict";a.r(t);var n=a("gDS+"),l=a.n(n),o=a("t3Un");var s=a("ZySA"),i=(a("7Qib"),{directives:{waves:s.a},filters:{statusFilter:function(e){return{published:"success",draft:"info",deleted:"danger"}[e]}},data:function(){return{ruleForm:{id:null,statusCode:2},openOnUserMsgFlag:!1,listLoading:!0,openAddFlag:!1,tableData:[],getdata:{num:1,size:20,name:void 0},selectData:[{name:"已申请",value:1},{name:"审核中",value:2},{name:"签约中",value:3},{name:"放款中",value:4},{name:"还款中",value:5},{name:"申请不通过",value:0}],total:null,nowUid:null,temp:{name:null,mail:null,card:null,phone:null,bankCard:null,education:null,marry:null,income:null,address1:null,address2:null,linkMan:null,linkManPhone:null,loginPhone:null,status:null,positionCode:null}}},created:function(){this.httpinit()},methods:{onviewClass:function(e){this.$router.push({path:"/school/class",query:{id:e}})},handleCurrentChange:function(e){console.log("当前页: "+e),console.log(this.getdata),this.httpinit()},handleSizeChange:function(e){console.log("每页 "+e+" 条"),this.getdata.size=e,this.httpinit()},httpinit:function(){var e=this;this.listLoading=!0,this.getdata.name||(this.getdata.name=void 0),function(e){return Object(o.a)({url:"/api/generalUser/listByPage",method:"post",data:e})}(this.getdata).then(function(t){var a=t.data;100==a.code&&(e.tableData=a.data.data,e.total=parseInt(a.data.totalNums)),e.listLoading=!1})},openAdd:function(){this.openAddFlag=!0,this.ruleForm={name:""}},onAddSchool:function(e){var t=this;this.$refs[e].validate(function(e){if(!e)return console.log("error submit!!"),!1;save({statusCode:t.ruleForm.statusCode,id:t.ruleForm.id}).then(function(e){100===e.data.code&&(t.$message({message:"更新成功",type:"success"}),t.openAddFlag=!1,t.httpinit())})})},ondelete:function(e){var t=this;this.$confirm("确定删除此用户吗？","删除",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){(function(e){return Object(o.a)({url:"/api/generalUser/delete",method:"post",data:e})})({ids:e.id}).then(function(e){var a=e.data;100==a.code?(t.$message({type:"success",message:"删除成功!"}),t.httpinit()):t.$message({type:"error",message:a.msg})})}).catch(function(){})},onEdit:function(e){this.ruleForm=JSON.parse(l()(e)),this.openAddFlag=!0},onUserMsg:function(e){var t=this;this.openOnUserMsgFlag=!0,function(e){return Object(o.a)({url:"/api/generalUser/getOne",method:"post",data:e})}({id:JSON.parse(l()(e)).id}).then(function(e){var a=e.data;100===a.code&&(t.temp=a.data,console.log(a))})}}}),r=a("KHd+"),c=Object(r.a)(i,function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"app-container"},[a("el-container",[a("el-header",[a("el-row",[a("el-col",{attrs:{span:4}},[a("el-input",{attrs:{placeholder:"搜索姓名或手机号","prefix-icon":"el-icon-search",clearable:""},on:{input:e.httpinit},model:{value:e.getdata.name,callback:function(t){e.$set(e.getdata,"name",t)},expression:"getdata.name"}})],1),e._v(" "),a("el-col",{attrs:{span:2}},[a("el-button",{staticStyle:{"margin-left":"10px"},attrs:{type:"primary"},on:{click:e.httpinit}},[e._v("搜索")])],1)],1)],1),e._v(" "),a("el-main",[a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.listLoading,expression:"listLoading"}],staticStyle:{width:"100%"},attrs:{data:e.tableData,fit:"","highlight-current-row":"",border:""}},[a("el-table-column",{attrs:{prop:"name",label:"姓名",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",[e._v(e._s(t.row.name))])]}}])}),e._v(" "),a("el-table-column",{attrs:{prop:"createTime",label:"注册时间",align:"center"}}),e._v(" "),a("el-table-column",{attrs:{prop:"loginPhone",label:"手机号",align:"center"}}),e._v(" "),a("el-table-column",{attrs:{prop:"mail",label:"邮箱",align:"center"}}),e._v(" "),a("el-table-column",{attrs:{prop:"card",label:"身份证号码",align:"center"}}),e._v(" "),a("el-table-column",{attrs:{prop:"bankCard",label:"银行卡",align:"center"}}),e._v(" "),a("el-table-column",{attrs:{prop:"name",label:"额度",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",[e._v(e._s(t.row.positionCode))])]}}])}),e._v(" "),a("el-table-column",{attrs:{prop:"address",label:"操作",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{type:"success",size:"small"},on:{click:function(a){e.onUserMsg(t.row)}}},[e._v("查看详细")]),e._v(" "),a("el-button",{attrs:{type:"danger",size:"small"},on:{click:function(a){e.ondelete(t.row)}}},[e._v("删除")])]}}])})],1)],1),e._v(" "),a("el-footer",[a("el-row",{attrs:{type:"flex",justify:"center"}},[a("el-col",{attrs:{span:5}},[a("el-pagination",{attrs:{"current-page":e.getdata.num,"page-sizes":[20,50,100,200],"page-size":e.getdata.size,total:e.total,layout:"sizes, prev, pager, next"},on:{"update:currentPage":function(t){e.$set(e.getdata,"num",t)},"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}})],1)],1)],1)],1),e._v(" "),a("el-dialog",{attrs:{visible:e.openAddFlag,title:"更新借款状态",width:"30%",center:""},on:{"update:visible":function(t){e.openAddFlag=t}}},[a("el-form",{ref:"ruleForm",attrs:{model:e.ruleForm,"label-width":"120px"}},[a("el-form-item",{attrs:{label:"更新状态为",prop:"statusCode"}},[a("el-select",{attrs:{filterable:"",placeholder:"请选择状态"},model:{value:e.getdata.statusCode,callback:function(t){e.$set(e.getdata,"statusCode",t)},expression:"getdata.statusCode"}},e._l(e.selectData,function(e){return a("el-option",{key:e.value,attrs:{label:e.name,value:e.value}})}))],1)],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.openAddFlag=!1}}},[e._v("取 消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.onAddSchool("ruleForm")}}},[e._v("确 定")])],1)],1),e._v(" "),a("el-dialog",{attrs:{title:"借款人信息",visible:e.openOnUserMsgFlag,"close-on-press-escape":!1},on:{"update:visible":function(t){e.openOnUserMsgFlag=t}}},[a("el-form",{ref:"dataForm",staticStyle:{width:"400px","margin-left":"50px"},attrs:{model:e.temp,"label-position":"left","label-width":"130px"}},[a("el-form-item",{attrs:{label:"姓名"}},[a("span",[e._v(e._s(e.temp.name))])]),e._v(" "),a("el-form-item",{attrs:{label:"邮箱"}},[a("span",[e._v(e._s(e.temp.mail))])]),e._v(" "),a("el-form-item",{attrs:{label:"借款手机号"}},[a("span",[e._v(e._s(e.temp.phone))])]),e._v(" "),a("el-form-item",{attrs:{label:"身份证号码"}},[a("span",[e._v(e._s(e.temp.card))])]),e._v(" "),a("el-form-item",{attrs:{label:"银行卡号"}},[a("span",[e._v(e._s(e.temp.bankCard))])]),e._v(" "),a("el-form-item",{attrs:{label:"学历"}},[a("span",[e._v(e._s(e.temp.education))])]),e._v(" "),a("el-form-item",{attrs:{label:"婚否"}},[a("span",[e._v(e._s(e.temp.marry))])]),e._v(" "),a("el-form-item",{attrs:{label:"年收入"}},[a("span",[e._v(e._s(e.temp.income))])]),e._v(" "),a("el-form-item",{attrs:{label:"工作地址"}},[a("span",[e._v(e._s(e.temp.address1))])]),e._v(" "),a("el-form-item",{attrs:{label:"现居地址"}},[a("span",[e._v(e._s(e.temp.address2))])]),e._v(" "),a("el-form-item",{attrs:{label:"紧急联系人"}},[a("span",[e._v(e._s(e.temp.linkMan))])]),e._v(" "),a("el-form-item",{attrs:{label:"紧急联系人手机号"}},[a("span",[e._v(e._s(e.temp.linkManPhone))])]),e._v(" "),a("el-form-item",{attrs:{label:"登录手机号"}},[a("span",[e._v(e._s(e.temp.loginPhone))])])],1),e._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.openOnUserMsgFlag=!1}}},[e._v("关闭")])],1)],1)],1)},[],!1,null,null,null);c.options.__file="user.vue";t.default=c.exports},ZySA:function(e,t,a){"use strict";var n=a("P2sY"),l=a.n(n),o=(a("jUE0"),{bind:function(e,t){e.addEventListener("click",function(a){var n=l()({},t.value),o=l()({ele:e,type:"hit",color:"rgba(0, 0, 0, 0.15)"},n),s=o.ele;if(s){s.style.position="relative",s.style.overflow="hidden";var i=s.getBoundingClientRect(),r=s.querySelector(".waves-ripple");switch(r?r.className="waves-ripple":((r=document.createElement("span")).className="waves-ripple",r.style.height=r.style.width=Math.max(i.width,i.height)+"px",s.appendChild(r)),o.type){case"center":r.style.top=i.height/2-r.offsetHeight/2+"px",r.style.left=i.width/2-r.offsetWidth/2+"px";break;default:r.style.top=(a.pageY-i.top-r.offsetHeight/2-document.documentElement.scrollTop||document.body.scrollTop)+"px",r.style.left=(a.pageX-i.left-r.offsetWidth/2-document.documentElement.scrollLeft||document.body.scrollLeft)+"px"}return r.style.backgroundColor=o.color,r.className="waves-ripple z-active",!1}},!1)}}),s=function(e){e.directive("waves",o)};window.Vue&&(window.waves=o,Vue.use(s)),o.install=s;t.a=o},"gDS+":function(e,t,a){e.exports={default:a("oh+g"),__esModule:!0}},jUE0:function(e,t,a){},"oh+g":function(e,t,a){var n=a("WEpk"),l=n.JSON||(n.JSON={stringify:JSON.stringify});e.exports=function(e){return l.stringify.apply(l,arguments)}}}]);