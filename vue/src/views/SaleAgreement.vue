<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd();getClients();">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
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

    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="id" label="id" ></el-table-column>
      <el-table-column prop="agreementCode" label="合同编号"></el-table-column>
      <el-table-column prop="status" label="状态"></el-table-column>
      <el-table-column prop="signTime" label="签订日期"></el-table-column>
      <el-table-column prop="clientname" label="客户名称"></el-table-column>
      <el-table-column prop="deliveryAddress" label="交货地址"></el-table-column>
      <el-table-column prop="distance" label="运输距离"></el-table-column>
      <el-table-column prop="deliveryTime" label="交货日期"></el-table-column>
      <el-table-column prop="operator" label="操作员"></el-table-column>

      <el-table-column label="操作"  width="300px" align="center">
        <template slot-scope = "scope">
          <el-button type="success" @click="getClients(); handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
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

    <el-dialog title="合同信息" :visible.sync="dialogFormVisible" width="30%">

      <el-form label-width="80px" size="small">
        <el-form-item label="合同编号">
          <el-input v-model="form.agreementCode" disabled></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio-button label="执行"></el-radio-button>
            <el-radio-button label="审核"></el-radio-button>
            <el-radio-button label="制单"></el-radio-button>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="签订日期">
          <el-input v-model="form.signTime" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="客户名称">
          <el-select clearable v-model="form.clientname" @change="insertData" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in clients" :key="item.name" :label="item" :value="item">
              <i :class="item.value" /> {{ item }}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="交货地点">
          <el-input v-model="form.deliveryAddress" ></el-input>
        </el-form-item>
        <el-form-item label="运输距离">
          <el-input v-model="form.distance" ></el-input>
        </el-form-item>
        <el-form-item label="交货日期">
          <el-date-picker
              v-model="form.deliveryTime"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
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
export default {
  name: "PurchaseAgreement",
  data(){
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: "",
      form:{},
      multipleSelection:[],
      dialogFormVisible:false,
      headerBg: 'headerBg',
      clients:[],
      ids:[],
      operator:''
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
      this.request.get("/saleAgreements/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
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
      console.log("strDate",strDate);
      return strDate
    },
    getClients(){
      this.request.get("/saleAgreements/clients").then(res => {
        if(res.code === '200'){
          console.log(res.data)
          this.clients = res.data
          this.load()
        }
        else{
          this.$message.error("客户信息请求失败")
          this.dialogFormVisible = false
          this.load()
        }

      })
    },
    insertData(){
          const len = 3
          const codeList = []
          const chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz0123456789'
          const charsLen = chars.length
          for (let i = 0; i < len; i++) {
            codeList.push(chars.charAt(Math.floor(Math.random() * charsLen)))
          }
          this.$set(this.form,'signTime', this.currentTime()); //正确赋值
          this.$set(this.form,'operator', this.operator); //正确赋值
          this.$set(this.form,'agreementCode', "HT"+this.currentTime()+codeList.join('')); //正确赋值
    },
    reset(){
      this.name = ""
      this.load()
    },
    handleEdit(row){
      this.form = row
      this.dialogFormVisible = true
    },
    del(id){
      this.request.delete("/saleAgreements/" + id).then(res =>{
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
      console.log(this.form)
      this.request.post("/saleAgreements", this.form).then(res =>{
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
      this.request.delete("/saleAgreements/del/batch", {data : ids}).then(res =>{
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

  }
}
</script>

<style scoped>

</style>