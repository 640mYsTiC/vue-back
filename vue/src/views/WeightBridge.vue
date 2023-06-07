<template>
    <div>
        <div id="newBridge">
            <span style="background-color: rgb(133, 195, 38); padding: 8px; border-radius: 10px; color: white; margin-right: 5px; margin-left: 10px;"> 新收料 </span>
            <el-button type="primary" @click="loading=!loading">启动自动执行机</el-button>
            <div style="margin-top: 5px; border: rgb(194, 211, 232) solid; padding-left: 10px; padding-top: 5px; width: 60%;"
                v-loading="loading" element-loading-text="自动执行中..."
                element-loading-spinner="el-icon-loading"
                element-loading-background="rgba(0, 0, 0, 0.8)"
            >
                <el-form :inline="true" class="demo-form-inline" >
                <el-form-item label="车号：">
                    <el-select clearable v-model="bridge.truckId" @change="getAgreementDetail" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in vehicle" :key="item.vehicleId" :label="item.plateNumber" :value="item.plateNumber">
                <i :class="item.plateNumber" /> {{ item.plateNumber }}
            </el-option>
            </el-select>
                </el-form-item>
                <el-form-item label="司机：">
                    <el-select clearable v-model="bridge.driver" @change="getAgreementDetail" placeholder="请选择" style="width: 100%">
                    <el-option v-for="item in driver" :key="item.driverId" :label="item.name" :value="item.name">
                <i :class="item.name" /> {{ item.name }}
            </el-option>
            </el-select>
                </el-form-item>
                <br/>
                <el-form-item label="供应原材料：">
                    <el-select clearable v-model="bridge.materialId" @change="getAgreementDetail" placeholder="请选择" style="width: 100%">
                        <el-option v-for="item in material" :key="item.id" :label="item.material" :value="item.material">
                        <i :class="item.material" /> {{ item.material }}
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="供应商：">
                    <el-select clearable v-model="bridge.supplier" @change="getAgreementDetail" placeholder="请选择" style="width: 100%">
                        <el-option v-for="item in material" :key="item.id" :label="item.supplier" :value="item.supplier">
                        <i :class="item.supplier" /> {{ item.supplier }}
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="储料仓：">
                    <el-input placeholder="" v-model="bridge.store"></el-input>
                </el-form-item>
                <br/>
                <el-form-item label="供货数量(t)：">
                    <el-input type="number" min="0" v-model="bridge.supplyNum"></el-input>
                </el-form-item>
                <el-form-item label="暗扣(按重量)：">
                    <el-input type="number" min="0" v-model="bridge.tareWeight"></el-input>
                </el-form-item>
                <br/>
                <el-form-item label="过磅人：">
                    <el-input v-model="bridge.person"></el-input>
                </el-form-item>
                <el-form-item label="备注：">
                    <el-input type="textarea" v-model="bridge.remark" style="width: 280px;"></el-input>
                </el-form-item>
            <el-button class="guobang" @click="save()" icon="el-icon-truck">过磅</el-button>
            </el-form>
            </div>
            
        </div>
        <div style="margin: 10px 0">
        <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
        <el-button class="ml-5" type="primary" @click="load()">搜索</el-button>
        <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
        <el-button type="success" style="float: right;" icon="el-icon-tickets">打印收料单</el-button>
        </div>

        <el-table
            v-loading="loading" element-loading-text="自动执行中..."
            element-loading-spinner="el-icon-loading"
            element-loading-background="rgba(0, 0, 0, 0.8)"
            :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column prop="id" label="收料单号"></el-table-column>
        <el-table-column prop="state" label="状态"></el-table-column>
        <el-table-column prop="truckId" label="运输车辆"></el-table-column>
        <el-table-column prop="driver" label="司机"></el-table-column>
        
        <el-table-column prop="materialId" label="供应原材料"></el-table-column>
        <el-table-column prop="supplyNum" label="供货数量"></el-table-column>
        <el-table-column prop="grossWeight" label="毛重(t)"></el-table-column>
        <el-table-column prop="tareWeight" label="皮重(t)"></el-table-column>
        <el-table-column prop="netWeight" label="净重(t)"></el-table-column>
        <el-table-column prop="person" label="过磅人"></el-table-column>
        <el-table-column prop="remark" label="备注"></el-table-column>

        <el-table-column label="操作"  width="300px" align="center">
        <template slot-scope = "scope">
            <el-popconfirm
                class="ml-5"
                confirm-button-text='确认'
                cancel-button-text='取消'
                icon="el-icon-info"
                icon-color="red"
                title="确定作废吗？"
                @confirm="del(scope.row.id)"
            >
            <el-button type="danger" slot = "reference">作废收料 <i class="el-icon-remove-outline"></i></el-button>
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
        <el-form-item label="收料编号">
            <el-input v-model="form.inCode" disabled></el-input>
        </el-form-item>

        <el-form-item label="收入材料">
            <el-select clearable v-model="form.inMaterial" @change="getAgreementDetail" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in materials" :key="item.name" :label="item" :value="item">
                <i :class="item.value" /> {{ item }}
            </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="供应商">
            <el-select clearable v-model="form.supplier" @change="insertData"
                    :disabled="!form.inMaterial" placeholder="请先选择材料" style="width: 100%">
            <el-option v-for="item in suppliers" :key="item.name" :label="item" :value="item">
                <i :class="item.value" /> {{ item }}
            </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="采购协议编号">
            <el-input v-model="form.agreementCode" disabled></el-input>
        </el-form-item>
        <el-form-item label="收料时间">
            <el-date-picker
                v-model="form.inTime"
                type="date"
                placeholder="选择日期">
            </el-date-picker>
        </el-form-item>
        <el-form-item label="收料重量">
            <el-input v-model="form.inWeight"></el-input>
        </el-form-item>
        <el-form-item label="储备库">
            <el-input v-model="form.inStorage"></el-input>
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
    name: "WeightBridge",
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
        materials:[],
        suppliers:[],
        code:'',
        bridge:{
            supplyNum:0,
            netWeight:0,
            grossWeight:0,
            tareWeight:0,
            state:''
        },
        loading:false,
        vehicle:[],
        driver:[],
        material:[],
    }
    },
    created() {
    // 请求分页查询数据
    const userInfo = JSON.parse(window.localStorage.getItem('user'));
    this.operator = userInfo.nickname;
    this.load()
    },
    filters: {
        getBySupplier(entities, supplier) {
        return entities.find(entity => entity.supplier === supplier);
        },
    },
    methods:{
    load() {
        this.request.get("/bridge/page", {
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
        this.request.get("/purchaseAgreements/page", {
            params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
        }
        }).then(res => {
        this.material = res.data.records
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
        this.request.get("/inMaterialmanage/materials").then(res => {
        if(res.code === '200'){
            console.log(res.data)
            this.materials = res.data
            this.load()
        }
        else{
            this.$message.error("材料信息请求失败")
            this.dialogFormVisible = false
            this.load()
        }
        })
    },
    getAgreementDetail(val){
        this.request.get("/inMaterialmanage/getAgreementSupplier/" + val).then(res => {
        if(res.code === '200'){
            this.suppliers = res.data
            const len = 3
            const codeList = []
            const chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz0123456789'
            const charsLen = chars.length
            for (let i = 0; i < len; i++) {
            codeList.push(chars.charAt(Math.floor(Math.random() * charsLen)))
            }
            this.$set(this.form,'inCode', "SH"+this.currentTime()+codeList.join('')); //正确赋值
        }
        else{
            this.$message.error("供应商信息请求失败")
            this.dialogFormVisible = false
            this.load()
        }
        })
    },
    insertData(val){
        this.request.get("/inMaterialmanage/getAgreementCode/" + val +"/" + this.form.inMaterial).then(res => {
        if(res.code === '200'){
            this.code = res.data.agreementCode
            console.log("this code"+res.data)
            this.$set(this.form,'agreementCode', this.code); //正确赋值
            this.$set(this.form,'operator', this.operator); //正确赋值
        }
        else{
            this.$message.error("协议信息请求失败")
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
        this.request.delete("/bridge/" + id).then(res =>{
        if(res.code === '200'){
            this.$message.success("作废成功")
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
        this.bridge.netWeight = this.bridge.supplyNum - this.bridge.tareWeight
        this.bridge.grossWeight = this.bridge.supplyNum
        this.bridge.state = '未开始'
        console.log(this.form)
        this.request.post("/bridge", this.bridge).then(res =>{
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
    .guobang{
        border-radius: 5px;
        width: 150px; height: 40px; float:right; margin-right: 20px; font-size: 20px; color: white; border: 0px; background-color: rgb(215, 122, 169);
    }
    .guobang:hover{
        background-color: rgb(80, 75, 79);
    }
</style>