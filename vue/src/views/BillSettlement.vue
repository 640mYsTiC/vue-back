<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd();getAgreementInfo();">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
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
      <el-table-column prop="settlementCode" label="结算编号"></el-table-column>
      <el-table-column prop="date" label="结算日期"></el-table-column>
      <el-table-column prop="agreementCode" label="协议编号"></el-table-column>
      <el-table-column prop="status" label="状态"></el-table-column>
      <el-table-column prop="supplier" label="供应商"></el-table-column>
      <el-table-column prop="material" label="材料"></el-table-column>
      <el-table-column prop="billTime" label="账期"></el-table-column>
      <el-table-column prop="weight" label="重量"></el-table-column>
      <el-table-column prop="transFee" label="运费"></el-table-column>
      <el-table-column prop="settlementFee" label="结算金额"></el-table-column>
      <el-table-column prop="operator" label="操作员"></el-table-column>
      <el-table-column label="操作"  width="300px" align="center">
        <template slot-scope = "scope">
          <el-button type="success" @click="getAgreementInfo(); handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
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

    <el-dialog title="协议信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" :rules = "rules" size="small" :model="form">
        <el-form-item label="结算编号">
          <el-input v-model="form.settlementCode" disabled></el-input>
        </el-form-item>
        <el-form-item label="结算日期">
          <el-input v-model="form.date" disabled></el-input>
        </el-form-item>
        <el-form-item label="协议编号">
          <el-select clearable v-model="form.agreementCode" @change="getAgreementDetail" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in codes" :key="item.name" :label="item" :value="item">
              <i :class="item.value" /> {{ item }}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="结算状态">
          <el-radio-group v-model="form.status">
            <el-radio-button label="制单"></el-radio-button>
            <el-radio-button label="审核中"></el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="供应商">
          <el-input v-model="form.supplier" disabled></el-input>
        </el-form-item>
        <el-form-item label="物料">
          .
          <el-input v-model="form.material" disabled></el-input>
        </el-form-item>
        <el-form-item label="账期">
          <el-date-picker
              v-model="form.billTime"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="重量" prop="weight">
          <el-input v-model="form.weight"  @change="countFee"></el-input>
        </el-form-item>
        <el-form-item label="运费" id = weight>
          <el-input v-model="form.transFee" disabled></el-input>
        </el-form-item>
        <el-form-item label="结算金额">
          <el-input v-model="form.settlementFee" disabled></el-input>
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
  name: "billSettlement",
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
      operator: '',
      codes:[],
      rules: {
        weight: [
          { required: true, message: '请输入重量', trigger: 'blur' },
        ],

      }
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
      this.request.get("/billSettlement/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        console.log(res.data)
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
    getAgreementInfo(){
      this.request.get("/billSettlement/agreementCode").then(res => {
        if(res.code === '200'){
          console.log(res.data)
          this.codes = res.data
          this.load()
        }
        else{
          this.$message.error("供应商信息请求失败")
          this.dialogFormVisible = false
          this.load()
        }

      })
    },
    getAgreementDetail(val){
      this.request.get("/billSettlement/agreementDetail/" + val).then(res => {
        if(res.code === '200'){
          const len = 3
          const codeList = []
          const chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz0123456789'
          const charsLen = chars.length
          for (let i = 0; i < len; i++) {
            codeList.push(chars.charAt(Math.floor(Math.random() * charsLen)))
          }
          console.log(res.data)
          this.$set(this.form,'settlementCode', "CJ"+this.currentTime() + codeList.join('')); //正确赋值
          this.$set(this.form,'date', this.currentTime()); //正确赋值
          this.$set(this.form,'supplier', res.data.supplier); //正确赋值
          this.$set(this.form,'material', res.data.material); //正确赋值
          this.$set(this.form,'operator', this.operator); //正确赋值
        }
        else{
          this.$message.error("供应商信息请求失败")
          this.dialogFormVisible = false
          this.load()
        }

      })
    },
    countFee(val){
      this.$set(this.form,'transFee', val*30); //正确赋值
      this.$set(this.form,'settlementFee', val*30+1500); //正确赋值
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
      this.request.delete("/billSettlement/" + id).then(res =>{
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
      this.request.post("/billSettlement", this.form).then(res =>{
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
      this.request.delete("/purchaseAgreements/del/batch", {data : ids}).then(res =>{
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