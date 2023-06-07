<template>
    <div>

    <div style="margin: 10px 0">
        浇筑时间：<el-date-picker v-model="product.taskDate" style="width: 150px;" align="right" type="date" placeholder="选择日期">
    </el-date-picker>
        调度状态：
        <el-select clearable v-model="product.state" placeholder="请选择" style="width: 120px;">
            <el-option value="等待">等待</el-option>
            <el-option value="生产中">生产中</el-option>
            <el-option value="暂停">暂停</el-option>
            <el-option value="完成">完成</el-option>
        </el-select>

        <el-button class="ml-5" type="primary" @click="search1()" plain>查询</el-button>
        <el-button class="ml-5" type="warning" @click="load();product={};" plain>重置</el-button>
    </div>

    <div >
        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange" style="width: 70%; display:inline-block">
            <el-table-column
                type="selection"
                width="55">
            </el-table-column>
            <el-table-column prop="id" label="任务编号" width="80"></el-table-column>
            <el-table-column prop="state" label="调度状态"></el-table-column>
            <el-table-column prop="taskDate" label="计划浇筑时间 "></el-table-column>
            <el-table-column prop="clientName" label="客户名称"></el-table-column>
            <el-table-column prop="projectName" label="工程名称"></el-table-column>
            <el-table-column prop="constructionSite" label="施工部位"></el-table-column>
            <el-table-column prop="concrete" label="混凝土标记"></el-table-column>
            <el-table-column prop="way" label="浇筑方式"></el-table-column>
            <el-table-column prop="planVolume" label="计划方量(t)"></el-table-column>
            <el-table-column prop="car" label="泵车"></el-table-column>
            <el-table-column prop="driver" label="司机"></el-table-column>
            <el-table-column prop="line" label="生产线"></el-table-column>
            <el-table-column label="操作"  width="200px" align="center">
            <template slot-scope = "scope">
                <el-button type="success" @click="handleEdit(scope.row)" style="margin-right: 5px;">派车</el-button>
                <el-popconfirm
                    class="ml-5"
                    confirm-button-text='确认'
                    cancel-button-text='取消'
                    icon="el-icon-info"
                    icon-color="red"
                    title="确定暂停吗？"
                    @confirm="form.state = '暂停';save()"
                >
                <el-button type="danger" slot = "reference" @click="stop(scope.row)">暂停</el-button>
            </el-popconfirm>
        </template>
        </el-table-column>
        </el-table>
        <el-table :data="vehicle" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange" style=" width: 28%; float: right;">
            <el-table-column prop="vehicleId" label="" width="80"></el-table-column>
            <el-table-column prop="plateNumber" label="泵车"></el-table-column>
            <el-table-column prop="capacity" label="方量 "></el-table-column>
            <el-table-column prop="driver" label="司机"></el-table-column>
            </el-table>
    </div>
    
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

    <el-dialog title="任务信息" :visible.sync="dialogFormVisible" width="30%">
        <el-form label-width="100px" size="small">
        <el-form-item label="客户名称">
            <el-input v-model="form.clientName" disabled></el-input>
        </el-form-item>
        <el-form-item label="工程名称">
            <el-input v-model="form.projectName" disabled></el-input>
        </el-form-item>
        <el-form-item label="施工部位">
            <el-input v-model="form.constructionSite" disabled></el-input>
        </el-form-item>
        <el-form-item label="混凝土标记">
            <el-input v-model="form.concrete" disabled></el-input>
        </el-form-item>
        <el-form-item label="浇筑方式">
            <el-input v-model="form.way" disabled></el-input>
        </el-form-item>
        <el-form-item label="调度状态">
            <el-radio-group v-model="form.state" disabled>
            <el-radio-button label="等待"></el-radio-button>
            <el-radio-button label="生产中"></el-radio-button>
            <el-radio-button label="暂停"></el-radio-button>
            <el-radio-button label="完成"></el-radio-button>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="计划浇筑时间">
            <el-date-picker
                v-model="form.taskDate"
                type="date"
                placeholder="选择日期" disabled>
            </el-date-picker>
        </el-form-item>
        <el-form-item label="计划方量(t)">
            <el-input type="number" v-model="form.planVolume" disabled @change="countFee"></el-input>
        </el-form-item>
        <el-form-item label="运输距离(km)">
            <el-input v-model="form.distance" type="number" disabled></el-input>
        </el-form-item>
        <el-form-item label="备注">
            <el-input v-model="form.remark" type="input" disabled></el-input>
        </el-form-item>
        <el-form-item label="泵车">
            <el-select clearable v-model="form.car" @change="getAgreementDetail" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in vehicle" :key="item.vehicleId" :label="item.plateNumber" :value="item.plateNumber">
                <i :class="item.plateNumber" /> {{ item.plateNumber }}
            </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="司机">
            <el-select clearable v-model="form.driver" @change="getAgreementDetail" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in driver" :key="item.driverId" :label="item.name" :value="item.name">
                <i :class="item.name" /> {{ item.name }}
            </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="生产线">
            <el-radio-group v-model="form.line">
            <el-radio-button label="1"></el-radio-button>
            <el-radio-button label="2"></el-radio-button>
            <el-radio-button label="3"></el-radio-button>
            </el-radio-group>
        </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="form.state = '生产中';save();">确 定</el-button>
        </div>
    </el-dialog>

    </div>
</template>

<script>
export default {
    name: "ProductTask",
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
        dialogFormVisible2:false,
        dialogFormVisible3:false,
        headerBg: 'headerBg',
        operator: '',
        codes:[],
        product:{
            distance:0,
            planVolume:0,
            schedule:'未审核'
        },
        search:{},
        vehicle:[],
        driver:[],
        visible:false,
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
        this.request.get("/productTask/page", {
        params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            name: JSON.stringify(this.search),
        }
        }).then(res => {
        console.log(res.data)
        this.tableData = res.data.records
        this.total = res.data.total
        })
        this.request.get("/driver/page", {
            params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
        }
        }).then(res => {
        this.driver = res.data.records
        })
        this.request.get("/vehicle/page", {
            params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
        }
        }).then(res => {
        this.vehicle = res.data.records
        })
    },
    search1(){
        console.log('11')
        this.product.taskDate =  (new Date(this.product.taskDate)).getTime();
        this.request.post("/productTask/search", JSON.stringify(this.product)
        ).then(res => {
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
    stop(row){
        this.form = row
    },
    del(id){
        this.request.delete("/productTask/" + id).then(res =>{
        if(res.code === '200'){
            this.$message.success("已作废")
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
        this.dialogFormVisible2 = true
        this.product={
            distance:0,
            planVolume:0,
            schedule:'未审核'
        }
    },
    save(){
        console.log(this.form)
        this.request.post("/productTask", this.form).then(res =>{
        if(res.code === '200'){
            this.$message.success("保存成功")
            this.dialogFormVisible = false
            this.dialogFormVisible2 = false
            this.load()
        }
        else{
            this.$message.error("保存失败")
            this.dialogFormVisible = false
            this.dialogFormVisible2 = false
            this.load()
        }
        })
    },
    save2(){
        this.request.post("/productTask", this.product).then(res =>{
        if(res.code === '200'){
            this.$message.success("保存成功")
            this.dialogFormVisible = false
            this.dialogFormVisible2 = false
            this.dialogFormVisible3 = false
            this.load()
        }
        else{
            this.$message.error("保存失败")
            this.dialogFormVisible = false
            this.dialogFormVisible2 = false
            this.dialogFormVisible3 = false
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
    //审核
    delBatch(){
        let ids = this.multipleSelection.map(v => v.id) //取得val对象中的id
        if(ids==''){
            this.$message.error('请选择要处理的数据！');
            return
        }
        if(ids.length>1){
            this.$message.error('请选择一条数据！');
            return
        }
        console.log(ids)
        this.request.get('/productTask/'+ids).then(res=>{
            console.log(res.data)
            this.product = res.data
            this.dialogFormVisible3=true
        })
        // this.request.delete("/purchaseAgreements/del/batch", {data : ids}).then(res =>{
        // if(res.code === '200'){
        //     this.$message.success("批量删除成功")
        //     this.dialogFormVisible = false
        //     this.load()
        // }
        // else{
        //     this.$message.error("批量删除失败")
        //     this.dialogFormVisible = false
        //     this.load()
        // }
        // })
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