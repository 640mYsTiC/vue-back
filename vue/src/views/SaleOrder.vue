<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd();getAgreementCodes();">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
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
      <el-table-column prop="orderCode" label="订单编号"></el-table-column>
      <el-table-column prop="orderDate" label="订货日期"></el-table-column>
      <el-table-column prop="status" label="订单状态"></el-table-column>
      <el-table-column prop="agreementCode" label="合同编号"></el-table-column>
      <el-table-column prop="pourTime" label="浇筑时间"></el-table-column>
      <el-table-column prop="pourMethod" label="浇筑方式"></el-table-column>
      <el-table-column prop="clientname" label="客户名称"></el-table-column>
      <el-table-column prop="estimatedVolume" label="计划方量"></el-table-column>
      <el-table-column prop="operator" label="操作员"></el-table-column>

      <el-table-column label="操作"  width="300px" align="center">
        <template slot-scope = "scope">
          <el-button type="success" @click="getAgreementCodes(); handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
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

    <el-dialog title="订单信息" :visible.sync="dialogFormVisible" width="30%">

      <el-form label-width="80px" size="small" :rules="rules" :model="form">
        <el-form-item label="订单编号">
          <el-input v-model="form.orderCode" disabled></el-input>
        </el-form-item>
        <el-form-item label="订货日期">
          <el-date-picker
              v-model="form.orderDate"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio-button label="执行"></el-radio-button>
            <el-radio-button label="审核"></el-radio-button>
            <el-radio-button label="制单"></el-radio-button>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="合同编号">
          <el-select clearable v-model="form.agreementCode" @change="insertData" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in clients" :key="item.name" :label="item" :value="item">
              <i :class="item.value" /> {{ item }}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="浇筑日期">
          <el-date-picker
              v-model="form.pourTime"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="浇筑方式">
          <el-select v-model="form.pourMethod" clearable placeholder="请选择">
            <el-option
                v-for="item in pourMethods"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="客户名称">
          <el-input v-model="form.clientname" disabled></el-input>
        </el-form-item>
        <el-form-item label="计划方量" prop="estimatedVolume">
          <el-input v-model="form.estimatedVolume"></el-input>
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
      form: {},
      multipleSelection: [],
      dialogFormVisible: false,
      headerBg: 'headerBg',
      clientname: '',
      clients: [],
      ids: [],
      pourMethods: [
        {
          value: '人工',
          label: '人工'
        },
        {
          value: '泵送',
          label: '泵送'
        },
        {
          value: '直卸',
          label: '直卸'
        },
        {
          value: '塔吊',
          label: '塔吊'
        },
        {
          value: '地泵',
          label: '地泵'
        },
      ],
      operator: '',
      rules: {
        estimatedVolume: [
          {required: true, message: '请输入计划方量', trigger: 'blur'},
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
      this.request.get("/saleOrder/page", {
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
    getAgreementCodes(){
      this.request.get("/saleOrder/agreementCodes").then(res => {
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
    insertData(val){
      this.request.get("/saleOrder/clientname/" + val).then(res => {
        if(res.code === '200'){
          this.clientname = res.data
          const len = 3
          const codeList = []
          const chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz0123456789'
          const charsLen = chars.length
          for (let i = 0; i < len; i++) {
            codeList.push(chars.charAt(Math.floor(Math.random() * charsLen)))
          }
          this.$set(this.form,'clientname', this.clientname);
          this.$set(this.form,'operator', this.operator); //正确赋值
          this.$set(this.form,'orderCode', "OD"+this.currentTime()+codeList.join('')); //正确赋值
        }
        else{
          this.$message.error("客户信息请求失败")
          this.dialogFormVisible = false
          this.load()
        }

      })

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
      this.request.delete("/saleOrder/" + id).then(res =>{
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
      this.request.post("/saleOrder", this.form).then(res =>{
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
      this.request.delete("/saleOrder/del/batch", {data : ids}).then(res =>{
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