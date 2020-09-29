<template>
  <div class="rentBooks">
    <assets-page
      :total="totalNumbers"
      :search-form="searchForm"
      :currentPage="searchForm.pageIndex"
      @current-change="handleCurrentChange"
      @data-add="showAdd"
      @data-cancel="handleCancel"
      @data-delete="handleDelete"
      @data-search="handleSearch">
      <el-form-item label="学生名字" slot="search">
        <el-input v-model="searchForm.studentName" placeholder="学生名字"></el-input>
      </el-form-item>
      <el-form-item label="班级" slot="search">
        <el-input v-model="searchForm.className" placeholder="班级名字"></el-input>
      </el-form-item>
      <el-form-item label="专业" slot="search">
        <el-select v-model="searchForm.majorId" clearable placeholder="请选择专业">
          <el-option :label="item.majorName" :value="item.id" v-for="(item, index) in majorNames"
                     :key="index"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="学院" slot="search">
        <el-select v-model="searchForm.departmentId" clearable placeholder="请选择学院">
          <el-option :label="item.departmentName" :value="item.id" v-for="(item, index) in departmentNames"
                     :key="index"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="书籍名字" slot="search">
        <el-input v-model="searchForm.bookName" placeholder="书籍名字"></el-input>
      </el-form-item>
      <el-form-item label="是否取书" slot="search">
        <el-select v-model="searchForm.isTake" clearable placeholder="请选择">
          <el-option :label="item.key" :value="item.value" v-for="(item, index) in trueOrFalse"
                     :key="index"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="是否归还" slot="search">
        <el-select v-model="searchForm.isReturn" clearable placeholder="请选择">
          <el-option :label="item.key" :value="item.value" v-for="(item, index) in trueOrFalse"
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
          <el-table-column label="学生名字" prop="studentName" align="center"></el-table-column>
          <el-table-column label="班级名字" prop="className" align="center"></el-table-column>
          <el-table-column label="专业名字" prop="majorName" align="center"></el-table-column>
          <el-table-column label="学院名字" prop="departmentName" align="center"></el-table-column>
          <el-table-column label="书籍名字" prop="bookName" align="center"></el-table-column>
          <el-table-column label="是否取书" prop="isTakeStr" align="center"></el-table-column>
          <el-table-column label="是否归还" prop="isReturnStr" align="center"></el-table-column>
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
          <el-form-item label="是否取书">
            <el-select v-model="formData.isTake" clearable placeholder="请选择">
              <el-option :label="item.key" :value="item.value" v-for="(item, index) in trueOrFalse"
                         :key="index"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否归还">
            <el-select v-model="formData.isReturn" clearable placeholder="请选择">
              <el-option :label="item.key" :value="item.value" v-for="(item, index) in trueOrFalse"
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

  </div>
</template>

<script>
  import { dataMixin } from '@/common/js/mixin'
  import routes from '@/common/js/config/routes'
  import AssetsPage from '@/views/project/task/components/assets-page-apply'
  import { queryRentBook, addRentBook, updateRentBook, deleteRentBook } from 'api/libraryAPI'
  import { emptyToNull } from '@/common/js/utils/form'
  import { listMajorNames } from 'api/majorAPI'
  import { listDepartmentNames } from 'api/departmentAPI'

  export default {
    name: 'traffic',
    mixins: [dataMixin],
    data () {
      return {
        departmentNames: [],
        majorNames: [],
        searchForm: {
          studentName: '',
          className: '',
          majorId: '',
          departmentId: '',
          bookName: '',
          isTake: null,
          isReturn: null,
          pageIndex: 1
        },
        formData: {
          studentName: '',
          studentId: null,
          bookName: null,
          bookId: null,
          isReturn: null,
          isTake: null,
          id: ''
        },
        trueOrFalse:[{'key':'是','value':true},{'key':'否','value':false}]
      }
    },
    created () {
      this.getDepartmentNames()
      this.getMajorNames()
      this.getPageData()
    },
    methods: {
      async getDepartmentNames () {
        const res = await listDepartmentNames()
        if (res.success) {
          this.departmentNames = res.data
        } else {
          this.getDepartmentNames()
        }
      },
      async getMajorNames () {
        const res = await listMajorNames()
        if (res.success) {
          this.majorNames = res.data
        } else {
          this.getMajorNames()
        }
      },
      keydownhandle (event) {
        const { key } = event
        // 或者根据键码判断, 下面过滤掉除数字和退格外的所有输入
        if (new RegExp(/[0-9]/).test(key) || key === 'Backspace') return;
        event.preventDefault()
      },
      showAdd () {
        this.dialogTitle = '课程新增'
        this.toggleDialog()
      },
      showEdit (data) {
        this.dialogTitle = '课程编辑'
        this.setFormData(data)
        this.formData.isTake = data.isTake
        this.formData.isReturn = data.isReturn
        this.toggleDialog()
      },
      async save () {
        if (!this._checkForm()) {
          return
        }
        let message = ''
        let res = null
        if (this.formData.id) {
          res = await updateRentBook(this.formData)
          message = '修改成功'
        } else {
          res = await addRentBook(this.formData)
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
        const res = await queryRentBook(emptyToNull(this.searchForm))
        if (res.success) {
          this.tableData = res.data.results
          this.totalNumbers = res.data.totalNumbers
          this.tableData.forEach(function (each) {
            if (each.isReturn) {
              each['isReturnStr'] = '是'
            } else {
              each['isReturnStr'] = '否'
            }
            if (each.isTake) {
              each['isTakeStr'] = '是'
            } else {
              each['isTakeStr'] = '否'
            }
          })
        } else {
          this.getPageData()
        }
      },
      async deletePageData () {
        const res = await deleteRentBook(this.multipleSelection)
        if (res.success) {
          this.$message.success('删除成功')
          this.getPageData()
        } else {
          this.deletePageData()
        }
      },
      _checkForm () {
        if (!this.formData.isReturn === null) {
          this.$message.error('请选择是否已经归还')
          return false
        }
        if (this.formData.studentId === null) {
          return false
        }
        if (this.formData.bookId === null) {
          return false
        }
        if (this.formData.bookName === null) {
          return false
        }
        if (this.formData.id === null) {
          return false
        }
        if (this.formData.isTake === null) {
          this.$message.error('请选是否已经取书')
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
  .rentBooks{
    padding: 20px;

    .device-icon {
      font-size: 28px;
    }
  }
</style>
