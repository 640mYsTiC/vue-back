<template>
    <div>
    <div style="margin: 10px 0">
        <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
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
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column prop="vehicleId" label="车辆编号" width="80"></el-table-column>
        <el-table-column prop="plateNumber" label="车牌号"></el-table-column>
        <el-table-column prop="capacity" label="容量(t) "></el-table-column>
        <el-table-column prop="purchaseDate" label="购买日期"></el-table-column>
        <el-table-column prop="state" label="状态"></el-table-column>
        <el-table-column label="操作"  width="300px" align="center">
        <template slot-scope = "scope">
            <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
            <el-popconfirm
                class="ml-5"
                confirm-button-text='确认'
                cancel-button-text='取消'
                icon="el-icon-info"
                icon-color="red"
                title="确定删除吗？"
                @confirm="del(scope.row.vehicleId)"
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

    <el-dialog title="车辆信息" :visible.sync="dialogFormVisible" width="30%">
        <el-form label-width="80px" size="small" :rules="rules" :model="form">
        <el-form-item label="车牌号" prop="plateNumber">
            <el-input v-model="form.plateNumber" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="容量" prop="capacity">
            <el-input v-model="form.capacity" type="number" min="0" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="购买日期">
            <el-date-picker v-model="form.purchaseDate" style="width: 150px;" align="right" type="date" placeholder="选择日期">
    </el-date-picker>
        </el-form-item>
        <el-form-item label="状态">
            <el-radio-group v-model="form.state">
            <el-radio-button label="空闲"></el-radio-button>
            <el-radio-button label="正在使用"></el-radio-button>
            <el-radio-button label="停用"></el-radio-button>
            </el-radio-group>
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
    name: "Vehicle",
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
        menuDialogVisible:false,
        headerBg: 'headerBg',
        props: {
        label: ''
        },
        ids:[],
      rules: {
        plateNumber: [
          { required: true, message: '请输入车牌号', trigger: 'blur' },
        ],
        capacity: [
          { required: true, message: '请输入载重', trigger: 'blur' },
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
        this.request.get("/vehicle/page", {
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
    reset(){
        this.name = ""
        this.load()
    },
    handleEdit(row){
        this.form = row
        this.dialogFormVisible = true
    },
    del(id){
        this.request.delete("/vehicle/" + id).then(res =>{
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
        this.request.post("/vehicle", this.form).then(res =>{
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
        this.request.delete("/vehicle/del/batch", {data : ids}).then(res =>{
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