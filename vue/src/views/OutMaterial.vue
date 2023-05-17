<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd();getProduct();">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
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
      <el-table-column prop="outCode" label="售料编号"></el-table-column>
      <el-table-column prop="outMaterial" label="供应材料"></el-table-column>
      <el-table-column prop="client" label="客户"></el-table-column>
      <el-table-column prop="outTime" label="出货时间"></el-table-column>
      <el-table-column prop="outWeight" label="出货重量"></el-table-column>
      <el-table-column prop="outStorage" label="储备库"></el-table-column>
      <el-table-column prop="operator" label="操作员"></el-table-column>

      <el-table-column label="操作"  width="300px" align="center">
        <template slot-scope = "scope">
          <el-button type="success" @click="getProduct(); handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
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
      <el-form label-width="80px" size="small">
        <el-form-item label="售料编号">
          <el-input v-model="form.outCode" disabled></el-input>
        </el-form-item>

        <el-form-item label="供应材料">
          <el-select clearable v-model="form.outMaterial" @change="getClient" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in products" :key="item.name" :label="item" :value="item">
              <i :class="item.value" /> {{ item }}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="客户">
          <el-select clearable v-model="form.client"
                     :disabled="!form.outMaterial" placeholder="请先选择材料" style="width: 100%">
            <el-option v-for="item in clients" :key="item.name" :label="item" :value="item">
              <i :class="item.value" /> {{ item }}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出货时间">
          <el-date-picker
              v-model="form.outTime"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="出货重量">
          <el-input v-model="form.outWeight"></el-input>
        </el-form-item>
        <el-form-item label="储备库">
          <el-input v-model="form.outStorage"></el-input>
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
  name: "InMaterial",
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
      products:[],
      clients:[],
      code:'',
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
      this.request.get("/outMaterialmanage/page", {
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
    getProduct(){
      this.request.get("/outMaterialmanage/productMaterial").then(res => {
        if(res.code === '200'){

          this.products = res.data
          const len = 3
          const codeList = []
          const chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz0123456789'
          const charsLen = chars.length
          for (let i = 0; i < len; i++) {
            codeList.push(chars.charAt(Math.floor(Math.random() * charsLen)))
          }
          this.$set(this.form,'outCode', "OT"+this.currentTime()+codeList.join('')); //正确赋值
          this.load()
        }
        else{
          this.$message.error("产品信息请求失败")
          this.dialogFormVisible = false
          this.load()
        }
      })
    },
    getClient(val){
      this.request.get("/outMaterialmanage/client/" + val).then(res => {
        if(res.code === '200'){
          this.clients = res.data
          this.$set(this.form,'operator', this.operator); //正确赋值
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
      this.request.delete("/outMaterialmanage/" + id).then(res =>{
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
      this.request.post("/outMaterialmanage", this.form).then(res =>{
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
      this.request.delete("/outMaterialmanage/del/batch", {data : ids}).then(res =>{
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