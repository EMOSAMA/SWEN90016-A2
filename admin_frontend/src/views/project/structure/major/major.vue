<template>
  <div class="major">
    <assets-page
      :total="totalNumbers"
      :search-form="searchForm"
      :currentPage="searchForm.pageIndex"
      @current-change="handleCurrentChange"
      @data-add="showAdd"
      @data-cancel="handleCancel"
      @data-delete="handleDelete"
      @data-search="handleSearch">
      <el-form-item label="专业名称" slot="search">
        <el-input v-model="searchForm.majorName" placeholder="专业名称"></el-input>
      </el-form-item>
      <el-form-item label="所属学院" slot="search">
        <el-select v-model="searchForm.departmentId" clearable placeholder="请选择学院">
          <el-option :label="item.departmentName" :value="item.id" v-for="(item, index) in departmentNames"
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
          <el-table-column label="专业ID" prop="id" align="center"></el-table-column>
          <el-table-column label="专业名称" prop="majorName" align="center"></el-table-column>
          <el-table-column label="专业描述" prop="majorDescription" align="center"></el-table-column>
          <el-table-column label="学院名称" prop="departmentName" align="center"></el-table-column>
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
          <el-form-item label="专业名称">
            <el-input placeholder="请输入专业名称" v-model="formData.majorName"></el-input>
          </el-form-item>
          <el-form-item label="专业描述">
            <el-input placeholder="请输入专业描述" v-model="formData.majorDescription"></el-input>
          </el-form-item>
          <el-form-item label="所属学院">
            <el-select v-model="formData.departmentName" clearable placeholder="请选择学院">
              <el-option :label="item.departmentName" :value="item.departmentName" v-for="(item, index) in departmentNames"
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
  import AssetsPage from '@/views/project/task/components/assets-page'
  import { queryMajor, addMajor, updateMajor, deleteMajor } from 'api/majorAPI'
  import { listDepartmentNames } from 'api/departmentAPI'
  import { emptyToNull } from '@/common/js/utils/form'

  export default {
    name: 'traffic',
    mixins: [dataMixin],
    data () {
      return {
        searchForm: {
          majorName: '',
          departmentId: '',
          pageIndex: 1
        },
        formData: {
          majorName: '',
          majorDescription: '',
          departmentName: ''
        },
        departmentNames: []
      }
    },
    created () {
      this.getDepartmentNames()
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
      showAdd () {
        this.dialogTitle = '专业新增'
        this.toggleDialog()
      },
      showEdit (data) {
        this.dialogTitle = '专业编辑'
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
          res = await updateMajor(this.formData)
          message = '修改成功'
        } else {
          res = await addMajor(this.formData)
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
        const res = await queryMajor(emptyToNull(this.searchForm))
        if (res.success) {
          this.tableData = res.data.results
          this.totalNumbers = res.data.totalNumbers
        } else {
          this.getPageData()
        }
      },
      async deletePageData () {
        const res = await deleteMajor(this.multipleSelection)
        if (res.success) {
          this.$message.success('删除成功')
          this.getPageData()
        } else {
          this.deletePageData()
        }
      },
      _checkForm () {
        if (!this.formData.departmentName) {
          this.$message.error('请填写学院名称')
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
  .major {
    padding: 20px;

    .device-icon {
      font-size: 28px;
    }
  }
</style>
