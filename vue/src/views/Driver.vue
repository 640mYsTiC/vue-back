<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确认'
          cancel-button-text='取消'
          icon="el-icon-info"
          icon-color="red"
          title="确定删除吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot = "reference" class="mr-5">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
      <el-upload :action="'http://' + serverIp + ':9090/user/import'" :show-file-list="false" accept=".xlsx"
                 :on-success="handleExcelImportSuccess" style="display: inline-block">
        <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="driverId" label="ID" width="80"></el-table-column>
      <el-table-column prop="name" label="司机姓名" width="140"></el-table-column>
      <el-table-column prop="phone" label="电话"></el-table-column>
      <el-table-column prop="driverLicense" label="驾照编号" width="80"></el-table-column>
      <el-table-column prop="drivingYears" label="驾龄"></el-table-column>
      <el-table-column prop="updatedAt" label="编辑日期"></el-table-column>

      <el-table-column label="操作"  width="300" align="center">
        <template v-slot = "scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确认'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="del(scope.row.driverId)"
          >
            <el-button type="danger" slot = "reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2, 5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small" :rules="rules" :model="form">
        <el-form-item label="司机姓名" prop="name">
          <el-input v-model="form.name" autocomplete="off" @change="insertValue"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" autocomplete="off" @change="insertValue"></el-input>
        </el-form-item>
        <el-form-item label="驾照编号" prop="license">
          <el-input v-model="form.driverLicense" autocomplete="off" @change="insertValue"></el-input>
        </el-form-item>
        <el-form-item label="驾龄" >
          <el-select v-model="form.drivingYears" @change="insertValue">
            <el-option v-for="i in 20" :key="i" :label="i + '年'" :value="i + '年'"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="编辑日期">
          <el-input v-model="form.updatedAt" autocomplete="off" disabled></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import {serverIp} from "../../public/config";

export default {
  name: "Driver",
  data(){
    return {
      serverIp: serverIp,
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      form:{},
      multipleSelection:[],
      dialogFormVisible:false,
      headerBg: 'headerBg',
      rules: {
        name: [
          { required: true, message: '请输入司机姓名', trigger: 'blur' },
        ],
        phone: [
          { required: true, message: '请输入司机电话', trigger: 'blur' },
        ],
        license: [
          { required: true, message: '请输入驾照编号', trigger: 'blur' },
        ],
      }
    }
  },
  created() {
    // 请求分页查询数据
    this.load()
  },

  methods:{
    load() {
      this.request.get("/driver/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {

        this.tableData = res.data.records
        this.total = res.data.total

      })
      this.request.get("/role").then(res => {
        this.roles = res.data
      })
    },
    currentTime() {
      var date = new Date();
      var year = date.getFullYear(); //月份从0~11，所以加一
      let month = date.getMonth();
      console.log("month",month);
      var dateArr = [
        date.getMonth() + 1,
        date.getDate(),
        date.getHours(),
        date.getMinutes(),
        date.getSeconds(),
      ];
      //如果格式是MM则需要此步骤，如果是M格式则此循环注释掉
      for (var i = 0; i < dateArr.length; i++) {
        if (dateArr[i] >= 1 && dateArr[i] <= 9) {
          dateArr[i] = "0" + dateArr[i];
        }
      }
      var strDate =
          year +
          dateArr[0] +
          dateArr[1]
      //此处可以拿外部的变量接收  strDate:2022-05-01 13:25:30
      //this.date = strDate;
      return strDate
    },
    reset(){
      this.name = ""
      this.load()
    },
    handleEdit(row){
      this.form = row
      this.dialogFormVisible = true

    },
    insertValue(){
      this.$set(this.form,'updatedAt', this.currentTime()); //正确赋值
    },
    del(id){
      this.request.delete("/driver/" + id).then(res =>{
        if(res.code === '200'){
          this.$message.success("删除成功")
          this.dialogFormVisible = false
          this.load()
        }
        else{
          this.$message.error("删除失败")
          this.dialogFormVisible = false
          this.load()
        }
      })
    },
    handleAdd(){
      this.dialogFormVisible = true
      this.form = {}
    },
    save(){
      this.request.post("/driver", this.form).then(res =>{
        if(res.code === '200'){
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        }
        else{
          this.$message.error("保存失败")
          this.dialogFormVisible = false
          this.load()
        }
      })
    },
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleSelectionChange(val){
      this.multipleSelection = val
    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.driverId) //取得val对象中的id
      this.request.delete("/driver/del/batch", {data : ids}).then(res =>{
        if(res.code === '200'){
          this.$message.success("批量删除成功")
          this.dialogFormVisible = false
          this.load()
        }
        else{
          this.$message.error("批量删除失败")
          this.dialogFormVisible = false
          this.load()
        }
      })
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },
    exp() {
      window.open(`http://${serverIp}:9090/driver/export`)
    },
    handleExcelImportSuccess(){
      this.$message.success("导入成功")
      this.load()
    },

  }
}
</script>

<style scoped>

</style>