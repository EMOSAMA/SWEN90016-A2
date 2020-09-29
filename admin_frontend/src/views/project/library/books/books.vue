<template>
  <div class="unit">
    <assets-page
      :total="totalNumbers"
      :search-form="searchForm"
      :currentPage="searchForm.pageIndex"
      @current-change="handleCurrentChange"
      @data-add="showAdd"
      @data-cancel="handleCancel"
      @data-delete="handleDelete"
      @data-search="handleSearch">
      <el-form-item label="课程名称" slot="search">
        <el-input v-model="searchForm.bookName" placeholder="书籍名称"></el-input>
      </el-form-item>
      <el-form-item label="作者名字" slot="search">
        <el-input v-model="searchForm.author" placeholder="作者名字"></el-input>
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
          <el-table-column label="书籍名称" prop="bookName" align="center"></el-table-column>
          <el-table-column label="作者名字" prop="author" align="center"></el-table-column>
          <el-table-column label="书籍描述" prop="bookDescription" align="center"></el-table-column>
          <el-table-column label="书籍总量" prop="totalAmount" align="center"></el-table-column>
          <el-table-column label="书籍出借量" prop="rentAmount" align="center"></el-table-column>
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
          <el-form-item label="书籍名称">
            <el-input placeholder="请输入书籍名称" v-model="formData.bookName"></el-input>
          </el-form-item>
          <el-form-item label="书籍描述">
            <el-input placeholder="请输入书籍描述" v-model="formData.bookDescription"></el-input>
          </el-form-item>
          <el-form-item label="作者名字">
            <el-input placeholder="请输入作者名字" v-model="formData.author"></el-input>
          </el-form-item>
          <el-form-item label="书籍总量">
            <el-input v-model="formData.totalAmount" placeholder="请输入书籍总量" @keydown.native="keydownhandle"></el-input>
          </el-form-item>
          <el-form-item label="书籍出借量">
            <el-input v-model="formData.rentAmount" placeholder="请输入已出借书籍数量" @keydown.native="keydownhandle"></el-input>
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
  import AssetsPage from '@/views/project/task/components/assets-page'
  import { queryBook, addBook, updateBook, deleteBook } from 'api/libraryAPI'
  import { emptyToNull } from '@/common/js/utils/form'

  export default {
    name: 'traffic',
    mixins: [dataMixin],
    data () {
      return {
        searchForm: {
          bookName: '',
          author: '',
          pageIndex: 1
        },
        formData: {
          bookName: '',
          bookDescription: '',
          author: '',
          totalAmount: null,
          rentAmount: 0,
          id: ''
        }
      }
    },
    created () {
      this.getPageData()
    },
    methods: {
      keydownhandle (event) {
        const { key } = event
        // 或者根据键码判断, 下面过滤掉除数字和退格外的所有输入
        if (new RegExp(/[0-9]/).test(key) || key === 'Backspace') return;
        event.preventDefault()
      },
      showAdd () {
        this.dialogTitle = '书籍新增'
        this.toggleDialog()
      },
      showEdit (data) {
        this.dialogTitle = '书籍编辑'
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
          res = await updateBook(this.formData)
          message = '修改成功'
        } else {
          res = await addBook(this.formData)
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
        const res = await queryBook(emptyToNull(this.searchForm))
        if (res.success) {
          this.tableData = res.data.results
          this.totalNumbers = res.data.totalNumbers
        } else {
          this.getPageData()
        }
      },
      async deletePageData () {
        const res = await deleteBook(this.multipleSelection)
        if (res.success) {
          this.$message.success('删除成功')
          this.getPageData()
        } else {
          this.deletePageData()
        }
      },
      _checkForm () {
        if (!this.formData.bookName) {
          this.$message.error('请填写书籍名称')
          return false
        }
        if (!this.formData.author) {
          this.$message.error('请填写作者')
          return false
        }
        if (!this.formData.bookDescription) {
          this.$message.error('请填写书籍描述')
          return false
        }
        if (!this.formData.totalAmount) {
          this.$message.error('请填写书籍总量')
          return false
        }
        if (!this.formData.rentAmount) {
          this.$message.error('请已租借数量')
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
  .unit {
    padding: 20px;

    .device-icon {
      font-size: 28px;
    }
  }
</style>
