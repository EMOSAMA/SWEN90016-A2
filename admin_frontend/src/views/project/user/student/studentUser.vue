<template>
  <div class="student">
    <assets-page
      :total="totalNumbers"
      :search-form="searchForm"
      :currentPage="searchForm.pageIndex"
      @current-change="handleCurrentChange"
      @data-add="showAdd"
      @data-cancel="handleCancel"
      @data-delete="handleDelete"
      @data-search="handleSearch">
      <el-form-item label="学生姓名" slot="search">
        <el-input v-model="searchForm.name" placeholder="学生名称"></el-input>
      </el-form-item>
      <el-form-item label="学生专业" slot="search">
        <el-select v-model="searchForm.majorId" clearable placeholder="请选择专业">
          <el-option :label="item.majorName" :value="item.id" v-for="(item, index) in majorNames"
                     :key="index"></el-option>
        </el-select>
      </el-form-item>
      <div slot="table">
        <el-table
          ref="multipleTable"
          :data="tableData"
          style="width: 100%"
          :border=true
          @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"></el-table-column>
          <el-table-column label="序号" type="index" :index="indexMethod" width="55" align="center"></el-table-column>
          <el-table-column label="学生账号" prop="username" align="center"></el-table-column>
          <el-table-column label="学生姓名" prop="name" align="center"></el-table-column>
          <el-table-column label="学生专业" prop="majorName" align="center"></el-table-column>
          <el-table-column label="出生日期" align="center">
            <template slot-scope="scope">
              {{formatDate(scope.row.birth)}}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="250" align="center">
            <template slot-scope="scope">
              <el-button type="primary" @click="showEdit(scope.row)">编辑</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </assets-page>
    <div class="dialog-wrapper">
      <el-dialog
        :title="dialogTitle"
        :visible.sync="showDialog"
        :close-on-click-modal=false
        width="600px"
        @closed="dialogClosed">
        <el-form :model="formData" label-width="100px">
          <el-form-item label="学生账号">
            <el-input placeholder="请输入学生账号" v-model="formData.username"></el-input>
          </el-form-item>
          <el-form-item label="账号密码">
            <el-input placeholder="请输入学生账号密码" v-model="formData.password"></el-input>
          </el-form-item>
          <el-form-item label="学生姓名">
            <el-input placeholder="请输入学生姓名" v-model="formData.name"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio :label="item.type" :value="item.type" v-for="(item, index) in genderTypes"
                        :key="index" v-model="formData.gender"></el-radio>
          </el-form-item>
          <el-form-item label="出生日期">
            <el-date-picker
              v-model="formData.birth"
              type="datetime"
              placeholder="请选择出生日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="专业">
            <el-select v-model="formData.majorId" clearable placeholder="请选择专业">
              <el-option :label="item.majorName" :value="item.id" v-for="(item, index) in majorNames"
                         :key="index"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="高中学校">
            <el-input placeholder="请输入高中学校" v-model="formData.highSchool"></el-input>
          </el-form-item>
          <el-form-item label="国籍">
            <el-input placeholder="请输入国籍" v-model="formData.nationality"></el-input>
          </el-form-item>
          <el-form-item label="身份证号码">
            <el-input placeholder="请输入身份证号码" v-model="formData.identityNumber"></el-input>
          </el-form-item>
          <el-form-item label="家庭住址">
            <el-input placeholder="请输入家庭住址" v-model="formData.homeAddress"></el-input>
          </el-form-item>
          <el-form-item label="联系电话">
            <el-input placeholder="请输入联系电话" v-model="formData.contactNumber"></el-input>
          </el-form-item>
          <el-form-item label="政治面貌">
            <el-input placeholder="可输入政治面貌" v-model="formData.politicalStatus"></el-input>
            <el-select  v-model="formData.politicalStatus" clearable placeholder="可选择的政治面貌">
              <el-option :label="item.type" :value="item.type" v-for="(item, index) in politicalStatus"
                         :key="index"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="save">保存</el-button>
            <el-button @click="toggleDialog">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
    <div>
      <a href="javascript:;" class="file">导入表格
        <input id="upload" type="file" @change="importfxx(this)"  accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
      </a>
    </div>
    <el-table
      ref="multipleTable"
      :data="formStudents"
      style="width: 100%"
      :border=true
      @selection-change="handleSelectionChange">
      <el-table-column label="序号" type="index" :index="indexMethod" width="55" align="center"></el-table-column>
      <el-table-column label="学生姓名" prop="name" align="center"></el-table-column>
      <el-table-column label="学生账号" prop="username" align="center"></el-table-column>
      <el-table-column label="账号密码" prop="password" align="center"></el-table-column>

      <el-table-column label="性别" prop="gender" align="center"></el-table-column>
      <el-table-column label="出生日期" prop="birth" align="center"></el-table-column>
      <el-table-column label="联系电话" prop="contactNumber" align="center"></el-table-column>
      <el-table-column label="高中学校" prop="highSchool" align="center"></el-table-column>
      <el-table-column label="家庭住址" prop="homeAddress" align="center"></el-table-column>
      <el-table-column label="身份证号码" prop="identityNumber" align="center"></el-table-column>
      <el-table-column label="国籍" prop="nationality" align="center"></el-table-column>
      <el-table-column label="政治面貌" prop="politicalStatus" align="center"></el-table-column>
      <el-table-column label="专业ID" prop="majorId" align="center"></el-table-column>
    </el-table>
    <el-button type="primary" @click="importStudents">保存</el-button>
  </div>
</template>

<script>
  import { dataMixin } from '@/common/js/mixin'
  import routes from '@/common/js/config/routes'
  import AssetsPage from '@/views/project/task/components/assets-page'
  import { queryStudent, addStudent, addStudents, updateStudent, deleteStudent } from 'api/studentAPI'
  import { listMajorNames } from 'api/majorAPI'
  import { emptyToNull } from '@/common/js/utils/form'
  import { dateFtt } from '@/common/js/utils'

  export default {
    name: 'traffic',
    mixins: [dataMixin],
    data () {
      return {
        searchForm: {
          name: '',
          majorId: '',
          pageIndex: 1
        },
        formData: {
          username: '',
          password: '',
          name: '',
          majorId: '',
          birth: '',
          classId: null,
          highSchool: null,
          gender: null,
          nationality: null,
          identityNumber: null,
          homeAddress: null,
          contactNumber: null,
          politicalStatus: null
        },
        formStudents: [],
        test: 'a',
        majorNames: [],
        genderTypes:[{'type':'男'},{'type':'女'}],
      politicalStatus: [{'type':'团员'},{'type':'党员'},{'type':'无'}]
      }
    },
    created () {
      this.getMajorNames()
      this.getPageData()
    },
    methods: {
      async importStudents () {
        let res = null
        res = await addStudents(this.formStudents)
        if (res.success) {
          this.$message.success('新增成功')
          this.formStudents = []
          this.getPageData()
        } else {
          this.importStudents()
        }
      },
      importfxx (obj) {
        let _this = this
        let inputDOM = this.$refs.inputer
        // 通过DOM取文件数据
        this.file = event.currentTarget.files[0]
        var rABS = false
        var f = this.file
        var reader = new FileReader()
        FileReader.prototype.readAsBinaryString = (f) => {
          var binary = ''
          var rABS = false
          var pt = this
          var wb
          var outdata
          var reader = new FileReader()
          reader.onload = (e) => {
            var bytes = new Uint8Array(reader.result)
            var length = bytes.byteLength
            for (var i = 0; i < length; i++) {
              binary += String.fromCharCode(bytes[i])
            }
            var XLSX = require('xlsx')
            if (rABS) {
              wb = XLSX.read(btoa( fixdata (binary)), {
                type: 'base64'
              })
            } else {
              wb = XLSX.read(binary, {
                type: 'binary'
              })
            }
            outdata = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]])
            this.da = [...outdata]
            let arr = []
            this.da.map(v => {
              let obj = {}
              obj.name = v.name
              obj.username = v.username
              obj.password = v.password
              obj.gender = v.gender
              obj.birth = v.birth
              obj.contactNumber = v.contactNumber
              obj.highSchool = v.highSchool
              obj.homeAddress = v.homeAddress
              obj.identityNumber = v.identityNumber
              obj.nationality = v.nationality
              obj.politicalStatus = v.politicalStatus
              obj.majorId = v.majorId
              arr.push(obj)
            })
            this.formStudents = arr
            console.log(this.formStudents)
          }
          reader.readAsArrayBuffer(f)
        }
        if (rABS) {
          reader.readAsArrayBuffer(f)
        } else {
          reader.readAsBinaryString(f)
        }
      },
      formatDate (date) {
        return dateFtt('yyyy-MM-dd', new Date(date))
      },
      async getMajorNames () {
        const res = await listMajorNames()
        if (res.success) {
          this.majorNames = res.data
        } else {
          this.getMajorNames()
        }
      },
      showAdd () {
        this.dialogTitle = '学生账号新增'
        this.toggleDialog()
      },
      showEdit (data) {
        this.dialogTitle = '学生账号编辑'
        this.setFormData(data)
        this.toggleDialog()
      },
      async save () {
        if (!this._checkForm()) {
          return
        }
        let message = ''
        let res = null
        if (this.formData.id) {
          res = await updateStudent(this.formData)
          message = '修改成功'
        } else {
          res = await addStudent(this.formData)
          message = '新增成功'
        }
        if (res.success) {
          this.$message.success(message)
          this.getPageData()
          this.toggleDialog()
        } else {
           this.save()
        }
      },
      async getPageData () {
        const res = await queryStudent(emptyToNull(this.searchForm))
        if (res.success) {
          this.tableData = res.data.results
          this.totalNumbers = res.data.totalNumbers
          console.log(this.tableData)
        } else {
          this.getPageData()
        }
      },
      async deletePageData () {
        const res = await deleteStudent(this.multipleSelection)
        if (res.success) {
          this.$message.success('删除成功')
          this.getPageData()
        } else {
          this.deletePageData()
        }
      },
      _checkForm () {
        if (!this.formData.username) {
          this.$message.error('请填写学生用户名')
          return false
        }
        if (!this.formData.password) {
          this.$message.error('请填写密码')
          return false
        }
        if (!this.formData.name) {
          this.$message.error('请填写学生姓名')
          return false
        }
        if (!this.formData.majorId) {
          this.$message.error('请填写学生专业')
          return false
        }
        if (!this.formData.birth) {
          this.$message.error('请填写学生出生日期')
          return false
        }
        if (!this.formData.gender) {
          this.$message.error('请选择性别')
          return false
        }
        return true
      }
    },
    components: {
      AssetsPage
    },
    computed: {
      routes () {
        return routes.student
      }
    }
  }
</script>

<style lang="scss" scoped>
  .student {
    padding: 20px;

    .student-icon {
      font-size: 28px;
    }
  }
</style>
