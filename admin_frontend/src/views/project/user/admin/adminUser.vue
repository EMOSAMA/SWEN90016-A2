<template>
  <div class="admin">
    <assets-page
      :total="totalNumbers"
      :search-form="searchForm"
      :currentPage="searchForm.pageIndex"
      @current-change="handleCurrentChange"
      @data-add="showAdd"
      @data-cancel="handleCancel"
      @data-delete="handleDelete"
      @data-search="handleSearch">
      <el-form-item label="管理员姓名" slot="search">
        <el-input v-model="searchForm.name" placeholder="管理员名称"></el-input>
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
          <el-table-column label="管理员账号" prop="username" align="center"></el-table-column>
          <el-table-column label="管理员姓名" prop="name" align="center"></el-table-column>
          <el-table-column label="邮箱" prop="email" align="center"></el-table-column>
          <el-table-column label="操作" width="120" align="center">
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
          <el-form-item label="管理员账号">
            <el-input placeholder="请输入管理员账号" v-model="formData.username"></el-input>
          </el-form-item>
          <el-form-item label="账号密码">
            <el-input placeholder="请输入管理员账号密码" v-model="formData.password"></el-input>
          </el-form-item>
          <el-form-item label="管理员姓名">
            <el-input placeholder="请输入管理员姓名" v-model="formData.name"></el-input>
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input placeholder="请输入邮箱" v-model="formData.email"></el-input>
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
  import AssetsPage from '@/views/project/task/components/assets-page'
  import { queryAdmin, addAdmin, updateAdmin, deleteAdmin } from 'api/adminAPI'
  import { emptyToNull } from '@/common/js/utils/form'

  export default {
    name: 'traffic',
    mixins: [dataMixin],
    data () {
      return {
        searchForm: {
          name: '',
          pageIndex: 1
        },
        formData: {
          username: '',
          password: '',
          name: '',
          email: ''
        }
      }
    },
    created () {
      this.getPageData()
    },
    methods: {
      showAdd () {
        this.dialogTitle = '管理员账号新增'
        this.toggleDialog()
      },
      showEdit (data) {
        this.dialogTitle = '管理员账号编辑'
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
          res = await updateAdmin(this.formData)
          message = '修改成功'
        } else {
          res = await addAdmin(this.formData)
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
        const res = await queryAdmin(emptyToNull(this.searchForm))
        if (res.success) {
          this.tableData = res.data.results
          this.totalNumbers = res.data.totalNumbers
        } else {
          this.getPageData()
        }
      },
      async deletePageData () {
        const res = await deleteAdmin(this.multipleSelection)
        if (res.success) {
          this.$message.success('删除成功')
          this.getPageData()
        } else {
          this.deletePageData()
        }
      },
      _checkForm () {
        if (!this.formData.username) {
          this.$message.error('请填写管理员用户名')
          return false
        }
        if (!this.formData.password) {
          this.$message.error('请填写密码')
          return false
        }
        if (!this.formData.name) {
          this.$message.error('请填写管理员姓名')
          return false
        }
        if (!this.formData.email) {
          this.$message.error('请填写邮箱')
          return false
        }
        return true
      }
    },
    components: {
      AssetsPage
    }
  }
</script>

<style lang="scss" scoped>
  .admin {
    padding: 20px;

    .device-icon {
      font-size: 28px;
    }
  }
</style>
