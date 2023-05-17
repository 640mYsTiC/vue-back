<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="username"></el-input>
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
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="clientname" label="单位名称" width="140"></el-table-column>
      <el-table-column prop="clienttype" label="单位类型" width="120"></el-table-column>
      <el-table-column prop="contactPhone" label="联系电话"></el-table-column>
      <el-table-column prop="address" label="联系地址"></el-table-column>
      <el-table-column prop="status" label="客户状态"></el-table-column>
      <el-table-column prop="operator" label="操作员"></el-table-column>

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
              @confirm="del(scope.row.id)"
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

    <el-dialog title="客户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="单位名称">
          <el-input v-model="form.clientname" ></el-input>
        </el-form-item>
        <el-form-item label="单位类型">
          <el-select v-model="form.clienttype" clearable placeholder="请选择">
            <el-option
                v-for="item in types"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="form.contactPhone" ></el-input>
        </el-form-item>
        <el-form-item label="联系地址">
          <el-input v-model="form.address" ></el-input>
        </el-form-item>
        <el-form-item label="客户状态" >
          <el-radio-group v-model="form.status" @change="insertOperator">
            <el-radio-button label="启用"></el-radio-button>
            <el-radio-button label="暂停"></el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="操作员">
          <el-input v-model="form.operator" disabled></el-input>
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
  name: "ClientManage",
  data(){
    return {
      serverIp: serverIp,
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      username: "",
      email: "",
      address: "",
      form:{},
      multipleSelection:[],
      dialogFormVisible:false,
      headerBg: 'headerBg',
      operator:'',
      types:[
        {
          value:'1号水泥',
          label:'1号水泥'
        },
        {
          value:'搅不干混凝土',
          label:'搅不干混凝土'
        },
        {
          value:'精加工花岗岩',
          label:'精加工花岗岩'
        },
        {
          value:'精加工石英砂',
          label:'精加工石英砂'
        },
      ]
    }
  },
  created() {
    // 请求分页查询数据
    const userInfo = JSON.parse(window.localStorage.getItem('user'));
    this.operator = userInfo.nickname;
    this.load()
  },
  methods:{
    load() {
      this.request.get("/clientManage/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    reset(){
      this.username = ""
      this.load()
    },
    handleEdit(row){
      this.form = row
      this.dialogFormVisible = true

    },
    del(id){
      this.request.delete("/clientManage/" + id).then(res =>{
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
    insertOperator(){
      this.$set(this.form,'operator', this.operator); //正确赋值
    },
    save(){
      this.request.post("/clientManage", this.form).then(res =>{
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
      let ids = this.multipleSelection.map(v => v.id) //取得val对象中的id
      this.request.delete("/clientManage/del/batch", {data : ids}).then(res =>{
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
      window.open(`http://${serverIp}:9090/clientManage/export`)
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